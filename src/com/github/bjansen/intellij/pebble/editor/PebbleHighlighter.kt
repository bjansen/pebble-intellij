package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.lang.PebbleLexerAdapter
import com.github.bjansen.intellij.pebble.psi.PebbleTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import java.util.*

class PebbleHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return PebbleLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        if (delimiters.contains(tokenType)) {
            return DELIMITER
        }
        if (keywords.contains(tokenType)) {
            return KEYWORDS
        }
        if (tokenType === PebbleTypes.STRING
                || tokenType == PebbleTypes.SINGLE_QUOTED_STRING) {
            return STRINGS
        }
        if (tokenType == PebbleTypes.COMMENT) {
            return COMMENTS
        }
        return emptyArray()
    }

    companion object highlights {
        val DELIMITER = arrayOf(
                createTextAttributesKey("PEBBLE_DELIM", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        )

        val KEYWORDS = arrayOf(
                createTextAttributesKey("PEBBLE_KW", DefaultLanguageHighlighterColors.KEYWORD)
        )

        val STRINGS = arrayOf(
                createTextAttributesKey("PEBBLE_STR", DefaultLanguageHighlighterColors.STRING)
        )

        val COMMENTS = arrayOf(
                createTextAttributesKey("PEBBLE_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT)
        )
    }

    private val keywords = Arrays.asList(
            PebbleTypes.TRUE, PebbleTypes.FALSE, PebbleTypes.EQUALS,
            PebbleTypes.CONTAINS, PebbleTypes.AND, PebbleTypes.OR, PebbleTypes.NOT,
            PebbleTypes.IS, PebbleTypes.NULL
    )

    private val delimiters = Arrays.asList(
            PebbleTypes.TAG_OPEN, PebbleTypes.TAG_CLOSE, PebbleTypes.VAR_OPEN, PebbleTypes.VAR_CLOSE
    )

}
