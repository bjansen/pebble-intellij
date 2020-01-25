package com.github.bjansen.intellij.pebble.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.ScopeNode

class PebbleForTag(node: ASTNode) : PebbleTagDirective(node), PsiNameIdentifierOwner, ScopeNode {

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

    override fun getNameIdentifier(): PsiElement? = getVariable()

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun setName(name: String): PsiElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getVariable(): PsiElement? {
        return PsiTreeUtil.findChildOfType(getOpeningTag(), PebbleInVariable::class.java)
    }

    private fun getOpeningTag(): PsiElement? {
        return node.firstChildNode?.psi
    }

    private fun getClosingTag(): PsiElement? {
        return node.lastChildNode?.psi
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
