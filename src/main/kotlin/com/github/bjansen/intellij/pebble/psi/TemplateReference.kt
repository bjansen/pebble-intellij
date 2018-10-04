package com.github.bjansen.intellij.pebble.psi

import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase

/**
 * A reference to another template, via includes, extends etc.
 */
class TemplateReference(element: PsiElement, rangeInElement: TextRange) : PsiReferenceBase<PsiElement>(element, rangeInElement) {

    override fun resolve(): PsiElement? {
        val referencedFile = findReferencedFile()
        if (referencedFile != null) {
            return PsiManager.getInstance(element.project).findFile(referencedFile)
        }
        return null
    }

    private fun findReferencedFile(): VirtualFile? {
        val fileName = rangeInElement.substring(element.text)

        // TODO resolve non-relative paths (but we need to know how the loader is configured in the current project 😟)
        return null
    }

    override fun getVariants(): Array<Any?> {
        return emptyArray()
    }
}
