package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.psi.PebbleArgumentList
import com.github.bjansen.intellij.pebble.psi.PebbleIdentifier
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.hint.api.impls.MethodParameterInfoHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.lang.parameterInfo.*
import com.intellij.lang.parameterInfo.ParameterInfoUtils.findParentOfType
import com.intellij.lang.parameterInfo.ParameterInfoUtils.getCurrentParameterIndex
import com.intellij.psi.*

class PebbleParameterInfoHandler : ParameterInfoHandler<PebbleArgumentList, PsiElement> {

    override fun updateParameterInfo(args: PebbleArgumentList, context: UpdateParameterInfoContext) {
        val index = getCurrentParameterIndex(args.node, context.offset, tokens[PebbleLexer.COMMA])
        context.setCurrentParameter(index)

//        context.highlightedParameter = context.objectsToView.first {
//            if (it is PsiMethod)
//                it.parameterList.parametersCount >= index
//            else
//                false
//        }
    }

    override fun updateUI(p: PsiElement?, context: ParameterInfoUIContext) {
        updatePresentation(p, context)
    }

    fun updatePresentation(p: PsiElement?, context: ParameterInfoUIContext): String? {
        if (p is PsiMethod) {
            return MethodParameterInfoHandler.updateMethodPresentation(p, PsiSubstitutor.EMPTY, context)
        }
        return null
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): PebbleArgumentList? {
        return findParentOfType(context.file, context.offset, PebbleArgumentList::class.java)
    }

    override fun getParameterCloseChars(): String? {
        return ParameterInfoUtils.DEFAULT_PARAMETER_CLOSE_CHARS
    }

    override fun findElementForParameterInfo(context: CreateParameterInfoContext): PebbleArgumentList? {
        val argList = findParentOfType(context.file, context.offset, PebbleArgumentList::class.java)

        if (argList != null) {
            val identifier = argList.parent.firstChild
            if (identifier is PebbleIdentifier) {
                context.itemsToShow = identifier.references.flatMap {
                    (it as PsiPolyVariantReference).multiResolve(false)
                            .map(ResolveResult::getElement)
                }.toTypedArray()
            }

            return argList
        }

        return null
    }

    override fun getParametersForDocumentation(p: PsiElement?, context: ParameterInfoContext?): Array<Any>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun tracksParameterIndex() = true

    override fun showParameterInfo(element: PebbleArgumentList, context: CreateParameterInfoContext) {
        context.showHint(element, element.textRange.startOffset, this)
    }

    override fun getParametersForLookup(item: LookupElement?, context: ParameterInfoContext?) = null

    override fun couldShowInLookup() = true
}
