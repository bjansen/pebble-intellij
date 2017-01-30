package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor

class PebbleFoldingBuilder : CustomFoldingBuilder() {
    override fun isRegionCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }

    override fun buildLanguageFoldRegions(descriptors: MutableList<FoldingDescriptor>,
                                          root: PsiElement, document: Document, quick: Boolean) {
//        try {
        root.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement?) {
                if (element is PebbleTagDirective) {
                    descriptors.add(FoldingDescriptor(element, element.textRange))
                }
                super.visitElement(element)
            }
        })
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String {
        return "..." // TODO
    }
}