package com.github.bjansen.intellij.pebble.liveTemplates

import com.github.bjansen.intellij.pebble.lang.PebbleFileViewProvider
import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider
import com.intellij.psi.PsiFile

/**
 * Enables Pebble live templates when the caret is outside of a directive.
 */
class PebbleTemplateContextType : TemplateContextType("Pebble", "Pebble") {
    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        if (file is PebbleFile) {
            val currentElement = file.findElementAt(offset)
            if (currentElement != null) {
                return currentElement.node.elementType == tokens[PebbleLexer.CONTENT]
                    || (file.viewProvider as PebbleFileViewProvider).templateDataLanguage.isKindOf(currentElement.language)
            }
        }

        return false
    }
}

class PebbleLiveTemplatesProvider: DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles(): Array<out String> {
        return arrayOf("/liveTemplates/pebble")
    }

    override fun getHiddenLiveTemplateFiles(): Array<out String>? {
        return null
    }
}
