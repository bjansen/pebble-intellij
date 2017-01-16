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
        val attributes: Array<TextAttributesKey>

        if (delimiters.contains(tokenType)) {
            attributes = DELIMITER
        } else if (keywords.contains(tokenType)) {
            attributes = KEYWORDS
        } else if (tokenType === PebbleTypes.STRING
                || tokenType == PebbleTypes.SINGLE_QUOTED_STRING) {
            attributes = STRINGS
        } else if (tokenType == PebbleTypes.COMMENT) {
            attributes = COMMENTS
        } else {
            attributes = emptyArray()
        }
        return pack(BACKGROUND, attributes)
    }

    companion object highlights {
        val BACKGROUND = createTextAttributesKey("PEBBLE_BACKGROUND",
                DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR as TextAttributesKey)

        val DELIMITER = arrayOf(
                createTextAttributesKey("PEBBLE_DELIMITER", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        )

        val KEYWORDS = arrayOf(
                createTextAttributesKey("PEBBLE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        )

        val STRINGS = arrayOf(
                createTextAttributesKey("PEBBLE_STRING", DefaultLanguageHighlighterColors.STRING)
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
