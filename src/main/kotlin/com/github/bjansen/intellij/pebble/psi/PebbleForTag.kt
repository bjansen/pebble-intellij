package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.psi.*
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.InheritanceUtil
import org.antlr.intellij.adaptor.psi.ScopeNode

class PebbleForTag(node: ASTNode) : PebblePsiElement(node), ScopeNode {

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {

        val myContainingFile = containingFile
        if (myContainingFile is PebbleFile) {
            if (!processor.execute(myContainingFile.loopImplicitVariable, state)) {
                return false
            }
        }

        getVariable(place)?.let {
            if (!processor.execute(it, state)) {
                return false
            }
        }

        val openingTag = getOpeningTag()
        val closingTag = getClosingTag()

        if (openingTag != null && closingTag != null
            && openingTag.textOffset <= place.textOffset && place.textOffset <= closingTag.textOffset) {

            var nextChild = openingTag.nextSibling

            while (nextChild != null && nextChild != closingTag) {
                nextChild.processDeclarations(processor, state, lastParent, place)
                nextChild = nextChild.nextSibling
            }
        }

        return true
    }

    private fun getOpeningTag(): PsiElement? {
        return node.firstChildNode?.psi
    }

    private fun getClosingTag(): PsiElement? {
        return node.lastChildNode?.psi
    }

    private fun getVariable(place: PsiElement): PebbleImplicitVariable? {
        val tagExpression = getOpeningTag()?.children?.find {
            it.node.elementType == rules[PebbleParser.RULE_expression]
        }

        if (tagExpression is PebblePsiElement && tagExpression.node.firstChildNode.elementType == rules[PebbleParser.RULE_in_expression]) {
            val varName = tagExpression.node.firstChildNode.firstChildNode.psi
            val varValue = tagExpression.node.firstChildNode.lastChildNode.psi

            val isInSameExpression = varValue.textRange.contains(place.textOffset)
            val isDefinedAfterPlace = varValue.textRange.startOffset > place.textRange.endOffset

            if (isInSameExpression || isDefinedAfterPlace) {
                return null // Avoids StackOverflowErrors
            }
            val variableType = inferVariableType(varValue)

            return PebbleImplicitVariable(varName.text, variableType, this, varName)
        }

        return null
    }

    private fun inferVariableType(iterableExpression: PsiElement): PsiType {
        val visitor = ExpressionTypeVisitor()
        iterableExpression.accept(visitor)

        val type = visitor.type

        if (type != null) {
            var iteratedType: PsiType? = null

            InheritanceUtil.processSuperTypes(type, true) {
                if (it.canonicalText.startsWith("java.lang.Iterable")
                        && it is PsiClassType && it.parameters.isNotEmpty()) {
                    iteratedType = it.parameters[0]
                }

                true
            }

            if (iteratedType != null) {
                return iteratedType as PsiType
            }
        }

        return PsiType.getJavaLangObject(
                PsiManager.getInstance(containingFile.project),
                GlobalSearchScope.allScope(containingFile.project)
        )
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
