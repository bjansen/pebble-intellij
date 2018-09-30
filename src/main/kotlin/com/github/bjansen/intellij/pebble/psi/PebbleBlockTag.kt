package com.github.bjansen.intellij.pebble.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil

class PebbleBlockTag(node: ASTNode) : PebbleTagDirective(node), PsiNameIdentifierOwner {

    override fun setName(name: String): PsiElement {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getName(): String? {
        return nameIdentifier?.text ?: super.getName()
    }

    override fun getNameIdentifier(): PsiElement? {
        return findBlockName()
    }

    private fun findBlockName(): PsiElement? {
        val id = getTagNameElement()

        if (id != null) {
            val nextVisibleLeaf = PsiTreeUtil.nextVisibleLeaf(id)
            if (nextVisibleLeaf is LeafPsiElement) {
                return nextVisibleLeaf.parent // a PebbleIdentifier
            }
        }

        return null
    }
}
