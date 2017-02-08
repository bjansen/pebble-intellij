package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.getPebbleCodeStyleSettings
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.util.TextRange
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

        if (element != null) {
            if (element.node.elementType == tokens[PebbleLexer.TAG_OPEN]) {
                highlightTagName(element)
            } else if (element.node.elementType == tokens[PebbleLexer.VERBATIM_TAG_OPEN]) {
                val range = TextRange.from(element.textOffset + element.text.indexOf("verbatim"), "verbatim".length)
                holder.createInfoAnnotation(range, null).textAttributes = PebbleHighlighter.highlights.KEYWORDS
                highlightDelimiters(element)
            } else if (element.node.elementType == tokens[PebbleLexer.VERBATIM_BODY]) {
                val range = TextRange.from(element.textOffset + element.text.lastIndexOf("endverbatim"), "endverbatim".length)
                holder.createInfoAnnotation(range, null).textAttributes = PebbleHighlighter.highlights.KEYWORDS
                highlightDelimiters(element)
            }
        }
    }

    fun highlightDelimiters(element: PsiElement) {
        val codeStyle = getPebbleCodeStyleSettings(element.project)
        val range = TextRange.from(
                element.textOffset + element.text.lastIndexOf(codeStyle.TAG_OPEN),
                codeStyle.TAG_OPEN.length)
        holder.createInfoAnnotation(range, null).textAttributes = PebbleHighlighter.highlights.DELIMITER

        val endRange = TextRange.from(
                element.textOffset + element.text.lastIndexOf(codeStyle.TAG_CLOSE),
                codeStyle.TAG_CLOSE.length)
        holder.createInfoAnnotation(endRange, null).textAttributes = PebbleHighlighter.highlights.DELIMITER
    }

    fun highlightTagName(tag: PsiElement) {
        val id = PsiTreeUtil.nextVisibleLeaf(tag)
        if (id != null &&
                id.node.elementType == tokens[PebbleLexer.ID_NAME]) {
            holder.createInfoAnnotation(id, null).textAttributes = PebbleHighlighter.highlights.KEYWORDS
        }
    }
}
