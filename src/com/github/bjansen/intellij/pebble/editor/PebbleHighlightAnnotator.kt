package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.parser.PebbleLexer
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.psi.util.PsiTreeUtil

class PebbleHighlightAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        element.accept(PebbleHighlightVisitor(holder))
    }

}

private class PebbleHighlightVisitor(val holder: AnnotationHolder) : PsiRecursiveElementVisitor() {

    override fun visitElement(element: PsiElement?) {
        super.visitElement(element)

        if (element != null
                && element.node.elementType == tokens[PebbleLexer.TAG_OPEN]) {
            highlightTagName(element)
        }
    }

    fun highlightTagName(tag: PsiElement) {
        val id = PsiTreeUtil.nextVisibleLeaf(tag)
        if (id != null &&
                id.node.elementType == tokens[PebbleLexer.ID_NAME]) {
            holder.createInfoAnnotation(id, null).textAttributes = PebbleHighlighter.highlights.KEYWORDS
        }
    }
}
