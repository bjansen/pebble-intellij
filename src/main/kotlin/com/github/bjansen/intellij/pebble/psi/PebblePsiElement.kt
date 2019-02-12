package com.github.bjansen.intellij.pebble.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiUtilCore
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode
import java.util.*

open class PebblePsiElement(node: ASTNode) : ANTLRPsiNode(node) {

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {

        children.forEach {
            // If it's a scope, we only process it if it contains [place]
            if (it !is ScopeNode || it.textRange.contains(place.textOffset)) {
                if (!it.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }

        return true
    }

    /**
     * Same as [ANTLRPsiNode.getChildren] except it also returns [com.intellij.psi.PsiComment]s.
     */
    override fun getChildren(): Array<PsiElement> {
        var psiChild: PsiElement? = firstChild ?: return PsiElement.EMPTY_ARRAY
        val result = ArrayList<PsiElement>()

        while (psiChild != null) {
            if (psiChild !is PsiWhiteSpace) {
                result.add(psiChild)
            }
            psiChild = psiChild.nextSibling
        }

        return PsiUtilCore.toPsiElementArray(result)
    }
}
