package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.ModuleUtil
import com.intellij.psi.*
import com.intellij.psi.impl.light.ImplicitVariableImpl
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.PlatformIcons
import com.intellij.util.ProcessingContext

class PebbleSpringCompletionProvider : CompletionProvider<CompletionParameters>() {

    companion object {
        val implicitFunctions = arrayOf("href", "message", "hasErrors", "hasGlobalErrors",
                "hasFieldErrors", "getAllErrors", "getGlobalErrors", "getFieldErrors")

        val implicitVariables = mapOf(
                "beans" to "java.util.Map<String, Object>",
                "request" to "javax.servlet.http.HttpServletRequest",
                "response" to "javax.servlet.http.HttpServletResponse",
                "session" to "javax.servlet.http.HttpSession"
        )

        fun getImplicitVariables(file: PebbleFile): List<ImplicitVariable> {
            val facade = JavaPsiFacade.getInstance(file.project)

            return implicitVariables.map {
                PebbleImplicitVariable(
                        it.key,
                        facade.elementFactory.createTypeFromText(it.value, file),
                        file
                )
            }
        }
    }

    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?,
                                result: CompletionResultSet) {
        if (isPebbleSpringAvailable(parameters.originalFile) || ApplicationManager.getApplication().isUnitTestMode) {
            if (parameters.position.parent.parent.node.elementType == rules[PebbleParser.RULE_term]) {
                // TODO maybe we should create a dedicated object like PebbleImplicitFunction, that would also
                // be used in references
                implicitFunctions.forEach {
                    result.addElement(
                            LookupElementBuilder.create(it)
                                    .withPresentableText(it + "()")
                                    .withIcon(PlatformIcons.METHOD_ICON)
                                    .withInsertHandler(ParenthesesInsertHandler.WITH_PARAMETERS)
                    )
                }
//                implicitVariables.forEach {
//                    result.addElement(
//                            LookupElementBuilder.create(it.key)
//                                    .withTypeText(it.value)
//                                    .withIcon(PlatformIcons.VARIABLE_ICON)
//                    )
//                }
            }
        }
    }

}

private fun getSearchScope(file: PsiFile): GlobalSearchScope? {
    val module = ModuleUtil.findModuleForPsiElement(file)

    return module?.getModuleWithDependenciesAndLibrariesScope(false)
}

fun isPebbleSpringAvailable(file: PsiFile): Boolean {
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

class PebbleImplicitVariable(name: String, type: PsiType, scope: PsiElement, val declaration: PsiElement? = null)
    : ImplicitVariableImpl(scope.manager, JavaPsiFacade.getElementFactory(scope.project).createIdentifier(name), type, false, scope) {

}