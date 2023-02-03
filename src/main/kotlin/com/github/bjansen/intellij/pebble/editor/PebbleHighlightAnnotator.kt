package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.SOFT_KEYWORDS
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.TAG_NAMES
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.getPebbleCodeStyleSettings
import com.github.bjansen.pebble.parser.PebbleLexer
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class PebbleHighlightAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (TAG_NAMES.contains(element.node.elementType)) {
            highlightTagName(element, holder)
        } else if (element.node.elementType == tokens[PebbleLexer.VERBATIM_TAG_OPEN]) {
            val range = TextRange.from(element.textOffset + element.text.indexOf("verbatim"), "verbatim".length)
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(range)
                .textAttributes(PebbleHighlighter.KEYWORDS)
                .create()
            highlightDelimiters(element, holder)
        } else if (element.node.elementType == tokens[PebbleLexer.VERBATIM_TAG_END]) {
            val range = TextRange.from(element.textOffset + element.text.lastIndexOf("endverbatim"), "endverbatim".length)
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(range)
                .textAttributes(PebbleHighlighter.KEYWORDS)
                .create()
            highlightDelimiters(element, holder)
        } else if (element.node.elementType in SOFT_KEYWORDS && element.node.treeParent.elementType != rules[PebbleParser.RULE_identifier]) {
            // A soft keyword that is not used as an identifier
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element)
                .textAttributes(PebbleHighlighter.KEYWORDS)
                .create()
        }
    }

    fun highlightDelimiters(element: PsiElement, holder: AnnotationHolder) {
        val codeStyle = getPebbleCodeStyleSettings(element.project)
        val range = TextRange.from(
                element.textOffset + element.text.lastIndexOf(codeStyle.tagOpen),
                codeStyle.tagOpen.length)
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(range)
            .textAttributes(PebbleHighlighter.DELIMITER)
            .create()

        val endRange = TextRange.from(
                element.textOffset + element.text.lastIndexOf(codeStyle.tagClose),
                codeStyle.tagClose.length)
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(endRange)
            .textAttributes(PebbleHighlighter.DELIMITER)
            .create()
    }

    fun highlightTagName(id: PsiElement, holder: AnnotationHolder) {
        val tag = PsiTreeUtil.prevVisibleLeaf(id)
        if (tag?.node?.elementType == tokens[PebbleLexer.TAG_OPEN]) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(id)
                .textAttributes(PebbleHighlighter.KEYWORDS)
                .create()
        }
    }
}
