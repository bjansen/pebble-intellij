package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleTag
import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.github.bjansen.intellij.pebble.psi.PebbleTypes
import com.github.bjansen.intellij.pebble.psi.PebbleVisitor
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class PebbleHighlightAnnotator : PebbleVisitor(), Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        element.accept(PebbleHighlightVisitor(holder))
    }

}

private class PebbleHighlightVisitor(val holder: AnnotationHolder) : PebbleVisitor() {
    override fun visitTagDirective(tag: PebbleTagDirective) {
        tag.children.forEach { it.accept(this) }
    }

    override fun visitTag(tag: PebbleTag) {
        super.visitTag(tag)
        highlightTagName(tag)
        tag.children.forEach { it.accept(this) }
    }

    fun highlightTagName(tag: PsiElement) {
        val id = PsiTreeUtil.nextVisibleLeaf(tag.firstChild)
        if (id != null &&
                (id.node.elementType == PebbleTypes.ID_NAME
                        || id.node.elementType == PebbleTypes.CUSTOM_TAG_NAME)) {
            holder.createInfoAnnotation(id, null).textAttributes = PebbleHighlighter.highlights.KEYWORDS
        }
    }
}