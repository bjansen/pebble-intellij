package com.github.bjansen.intellij.pebble.ext

import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleIdentifier
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.buildPsiTypeLookups
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.findMembersByName
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.findQualifyingMember
import com.github.bjansen.intellij.pebble.utils.ResourceUtil
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.pom.PomTargetPsiElement
import com.intellij.psi.*
import com.intellij.psi.PsiElementResolveResult.createResults
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.spring.SpringManager
import com.intellij.spring.contexts.model.SpringModel
import com.intellij.spring.model.CommonSpringBean
import com.intellij.spring.model.jam.JamPsiClassSpringBean
import com.intellij.spring.model.jam.JamPsiMethodSpringBean
import com.intellij.spring.model.pom.SpringBeanPomTargetUtils
import com.intellij.spring.model.utils.SpringModelSearchers
import com.intellij.spring.model.xml.AbstractDomSpringBean
import com.intellij.util.ProcessingContext
import icons.SpringApiIcons

object SpringExtension {
    private val key = Key.create<PsiClass>("PEBBLE_SPRING_CLASS")

    private fun getPebbleSpringClass(project: Project): PsiClass? {
        return ResourceUtil.loadPsiClassFromFile("/implicitCode/PebbleSpring.java", key, project)
    }

    fun getImplicitVariables(file: PebbleFile): List<PsiVariable> {
        if (isPebbleSpringAvailable(file) || ApplicationManager.getApplication().isUnitTestMode) {
            val clazz = getPebbleSpringClass(file.project)
            if (clazz != null) {
                return clazz.fields.asList()
            }
        }

        return emptyList()
    }

    fun getImplicitFunctions(file: PebbleFile): List<PsiMethod> {
        if (isPebbleSpringAvailable(file) || ApplicationManager.getApplication().isUnitTestMode) {
            val clazz = getPebbleSpringClass(file.project)
            if (clazz != null) {
                return clazz.methods.asList()
            }
        }

        return emptyList()
    }

    private fun getSearchScope(file: PsiFile): GlobalSearchScope? {
        val module = ModuleUtil.findModuleForPsiElement(file)

        return module?.getModuleWithDependenciesAndLibrariesScope(false)
    }

    internal fun isPebbleSpringAvailable(file: PsiFile): Boolean {
        val scope = getSearchScope(file)

        if (scope != null) {
            val spring3 = JavaPsiFacade.getInstance(file.project)
                    .findClass("com.mitchellbosecke.pebble.spring.PebbleView", scope)
            val spring4 = JavaPsiFacade.getInstance(file.project)
                    .findClass("com.mitchellbosecke.pebble.spring4.PebbleView", scope)

            return spring3 != null || spring4 != null
        }

        return false
    }
}

class PebbleSpringReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PebbleIdentifier::class.java),
                PebbleSpringReferenceProvider()
        )
    }
}

/**
 * Provides references to Spring beans in expressions like `beans.myBean`.
 */
class PebbleSpringReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        return if (SpringExtension.isPebbleSpringAvailable(element.containingFile)) {
            arrayOf(PebbleSpringReference(element, TextRange.from(0, element.node.textLength)))
        } else {
            emptyArray()
        }
    }
}

class PebbleSpringReference(private val psi: PsiElement, private val range: TextRange)
    : PsiPolyVariantReferenceBase<PsiElement>(psi, range) {

    private fun isBeansField(field: PsiField) =
            field.name == "beans" && field.containingClass?.qualifiedName == "PebbleSpring"

    private fun getSpringModel(context: PsiElement): SpringModel? {
        val module = ModuleUtil.findModuleForPsiElement(context)
        if (module != null) {
            return SpringManager.getInstance(context.project).getCombinedModel(module)
        }
        return null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val qualifyingMember = findQualifyingMember(psi)
        val referenceText = range.substring(psi.text)

        if (qualifyingMember is PsiField && isBeansField(qualifyingMember)) {
            val model = getSpringModel(psi)
            if (model != null) {
                val bean = SpringModelSearchers.findBean(model, referenceText)
                if (bean != null) {
                    return createResults(bean.psiElement)
                }
            }
        } else if (qualifyingMember is PomTargetPsiElement) {
            val springBeanType = getBeanType(SpringBeanPomTargetUtils.getSpringBean(qualifyingMember))
            if (springBeanType is PsiClassType) {
                return createResults(findMembersByName(springBeanType.resolve(), referenceText))
            }
        }

        return emptyArray()
    }

    override fun getVariants(): Array<Any> {
        val qualifyingMember = findQualifyingMember(psi)

        if (qualifyingMember is PsiField && isBeansField(qualifyingMember)) {
            val model = getSpringModel(psi)
            if (model != null) {
                return model.allCommonBeans
                        .filter { getBeanType(it.springBean) != null }
                        .mapNotNull {
                            val lookup = LookupElementBuilder.create(it.name ?: "?")
                                    .withTypeText(it.beanClass?.name)
                                    .withIcon(SpringApiIcons.SpringBean)
                            PrioritizedLookupElement.withPriority(lookup, 1.0)
                        }
                        .toTypedArray()
            }
        } else if (qualifyingMember is PomTargetPsiElement) {
            val springBeanType = getBeanType(SpringBeanPomTargetUtils.getSpringBean(qualifyingMember))
            if (springBeanType is PsiClassType) {
                return buildPsiTypeLookups(springBeanType)
            }
        }

        return emptyArray()
    }

    /**
     * Workaround for com.intellij.spring.model.CommonSpringBean.getBeanType() which is not in IDEA 15
     */
    private fun getBeanType(springBean: CommonSpringBean?): PsiType? {
        return when (springBean) {
            is AbstractDomSpringBean -> {
                val clazz = springBean.getBeanClass(null, true)
                return if (clazz == null) null else PsiTypesUtil.getClassType(clazz)
            }
            is JamPsiClassSpringBean -> PsiTypesUtil.getClassType(springBean.psiElement)
            is JamPsiMethodSpringBean -> springBean.psiElement.returnType
            else -> null
        }
    }
}
