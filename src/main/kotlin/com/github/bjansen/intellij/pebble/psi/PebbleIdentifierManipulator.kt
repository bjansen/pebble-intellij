package com.github.bjansen.intellij.pebble.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.ElementManipulator

class PebbleIdentifierManipulator : ElementManipulator<PebbleIdentifier> {
    override fun handleContentChange(element: PebbleIdentifier, newContent: String?): PebbleIdentifier {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleContentChange(element: PebbleIdentifier, range: TextRange, newContent: String?): PebbleIdentifier {
        if (newContent != null) {
            return element.replace(psiElementFactory.createIdentifier(newContent, element.project)) as PebbleIdentifier
        }
        return element
    }

    override fun getRangeInElement(element: PebbleIdentifier): TextRange {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
