package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.psi.*
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import org.antlr.jetbrains.adaptor.psi.ScopeNode

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

        getVariable()?.let {
            val javaObject = PsiType.getJavaLangObject(
                PsiManager.getInstance(containingFile.project),
                GlobalSearchScope.projectScope(containingFile.project)
            )
            val implicitVar = PebbleImplicitVariable(it.text, javaObject, this, null)

            if (!processor.execute(implicitVar, state)) {
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

    private fun getVariable(): PsiElement? {
        val tag = getOpeningTag()

        if (tag != null) {
            val expr = tag.children.find { it.node.elementType == rules[PebbleParser.RULE_expression] }

            if (expr is PebblePsiElement && expr.node.firstChildNode.elementType == rules[PebbleParser.RULE_in_expression]) {
                return expr.node.firstChildNode.firstChildNode.psi
            }
        }

        return null
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
