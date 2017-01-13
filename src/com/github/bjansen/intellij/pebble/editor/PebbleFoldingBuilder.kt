package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

class PebbleFoldingBuilder : CustomFoldingBuilder() {
    override fun isRegionCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }

    override fun buildLanguageFoldRegions(descriptors: MutableList<FoldingDescriptor>,
                                          root: PsiElement, document: Document, quick: Boolean) {
//        try {
            val firstChild = root.firstChild
            if (firstChild != null) {
                collectRegions(firstChild, descriptors)
            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    fun collectRegions(node: PsiElement, descriptors: MutableList<FoldingDescriptor>) {
        var child: PsiElement? = node

        while (child != null) {
            val elType = child.node.elementType

            if (elType == PebbleTypes.COMMENT_DIRECTIVE
                    || elType == PebbleTypes.TAG_DIRECTIVE) {
                descriptors.add(FoldingDescriptor(child, child.textRange))
            }

            child = child.nextSibling
        }
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String {
        return "..." // TODO
    }
}