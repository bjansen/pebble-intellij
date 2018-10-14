package com.github.bjansen.intellij.pebble.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil

class PebbleSetTag(node: ASTNode) : PebbleTagDirective(node), PsiNameIdentifierOwner {

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun getNameIdentifier(): PsiElement? {
        val tagNameElement = getTagNameElement()

        if (tagNameElement != null) {
            return PsiTreeUtil.nextVisibleLeaf(tagNameElement)?.parent
        }

        return null
    }

    override fun setName(name: String): PsiElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {

        return processor.execute(this, state)
    }
}
