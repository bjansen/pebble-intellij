package com.github.bjansen.intellij.pebble.ext

import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleIdentifier
import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper.buildPsiTypeLookups
import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper.findMemberByName
import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper.findQualifyingMember
import com.google.common.io.CharStreams
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.pom.PomTargetPsiElement
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.spring.SpringManager
import com.intellij.spring.contexts.model.SpringModel
import com.intellij.spring.model.pom.SpringBeanPomTargetUtils
import com.intellij.spring.model.utils.SpringModelSearchers
import com.intellij.util.ProcessingContext
import icons.SpringApiIcons
import java.io.InputStreamReader

object springExtension {
    private val key = Key.create<PsiClass>("PEBBLE_SPRING_CLASS")

    private fun getPebbleSpringClass(project: Project): PsiClass? {
        val cached = project.getUserData(key)

        if (cached != null) {
            return cached
        }

        val resource = springExtension::class.java
                .getResourceAsStream("/implicitCode/PebbleSpring.java")

        if (resource != null) {
            val text = CharStreams.toString(InputStreamReader(resource, Charsets.UTF_8))
            val javaFile = PsiFileFactory.getInstance(project)
                    .createFileFromText("a.java", JavaFileType.INSTANCE, text) as PsiJavaFile
            val clazz = javaFile.classes[0]
            project.putUserData(key, clazz)
            return clazz
        }

        project.putUserData(key, null)

        return null
    }

    fun getImplicitVariables(file: PebbleFile): List<PsiVariable> {
        if (isPebbleSpringAvailable(file) || ApplicationManager.getApplication().isUnitTestMode) {
            val clazz = getPebbleSpringClass(file.project)
            if (clazz != null) {
                return clazz.allFields.asList()
            }
        }

        return emptyList()
    }

    fun getImplicitFunctions(file: PebbleFile): List<PsiMethod> {
        if (isPebbleSpringAvailable(file) || ApplicationManager.getApplication().isUnitTestMode) {
            val clazz = getPebbleSpringClass(file.project)
            if (clazz != null) {
                return clazz.allMethods.asList()
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
        return if (springExtension.isPebbleSpringAvailable(element.containingFile)) {
            arrayOf(PebbleSpringReference(element, TextRange.from(0, element.node.textLength)))
        } else {
            emptyArray()
        }
    }
}

class PebbleSpringReference(private val psi: PsiElement, private val range: TextRange)
    : PsiReferenceBase<PsiElement>(psi, range) {

    private fun isBeansField(field: PsiField) =
            field.name == "beans" && field.containingClass?.qualifiedName == "PebbleSpring"

    private fun getSpringModel(context: PsiElement): SpringModel? {
        val module = ModuleUtil.findModuleForPsiElement(context)
        if (module != null) {
            return SpringManager.getInstance(context.project).getCombinedModel(module)
        }
        return null
    }

    override fun resolve(): PsiElement? {
        val qualifyingMember = findQualifyingMember(psi)
        val referenceText = range.substring(psi.text)

        if (qualifyingMember is PsiField && isBeansField(qualifyingMember)) {
            val model = getSpringModel(psi)
            if (model != null) {
                val bean = SpringModelSearchers.findBean(model, referenceText)
                if (bean != null) {
                    return bean.psiElement
                }
            }
        } else if (qualifyingMember is PomTargetPsiElement) {
            val springBeanType = SpringBeanPomTargetUtils.getSpringBean(qualifyingMember)?.beanType
            if (springBeanType is PsiClassType) {
                return findMemberByName(springBeanType.resolve(), referenceText)
            }
        }

        return null
    }

    override fun getVariants(): Array<Any> {
        val qualifyingMember = findQualifyingMember(psi)

        if (qualifyingMember is PsiField && isBeansField(qualifyingMember)) {
            val model = getSpringModel(psi)
            if (model != null) {
                val beans = arrayListOf<LookupElement>()

                model.processModels({
                    beans.addAll(
                            it.allCommonBeans
                                    .filter { it.springBean.beanType != null }
                                    .mapNotNull {
                                        LookupElementBuilder.create(it.name ?: "?")
                                                .withTypeText(it.beanClass?.name)
                                                .withIcon(SpringApiIcons.SpringBean)
                                    }
                    )
                    true
                })
                return beans.toTypedArray()
            }
        } else if (qualifyingMember is PomTargetPsiElement) {
            val springBeanType = SpringBeanPomTargetUtils.getSpringBean(qualifyingMember)?.beanType
            if (springBeanType is PsiClassType) {
                return buildPsiTypeLookups(springBeanType)
            }
        }

        return emptyArray()
    }
}
