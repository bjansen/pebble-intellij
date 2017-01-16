package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.lang.PebbleLexerAdapter
import com.github.bjansen.intellij.pebble.psi.PebbleTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import java.util.*

class PebbleHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return PebbleLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        val attributes: TextAttributesKey?

        if (delimiters.contains(tokenType)) {
            attributes = DELIMITER
        } else if (keywords.contains(tokenType)) {
            attributes = KEYWORDS
        } else if (braces.contains(tokenType)) {
            attributes = BRACES
        } else if (brackets.contains(tokenType)) {
            attributes = BRACKETS
        } else if (operators.contains(tokenType)) {
            attributes = OPERATORS
        } else if (parens.contains(tokenType)) {
            attributes = PARENTHESES
        } else if (strings.contains(tokenType)) {
            attributes = STRINGS
        } else if (tokenType == PebbleTypes.COMMENT) {
            attributes = COMMENT
        } else if (tokenType == PebbleTypes.ID_NAME) {
            attributes = IDENTIFIER
        } else if (tokenType == PebbleTypes.NUMERIC) {
            attributes = NUMBER
        } else if (tokenType == TokenType.BAD_CHARACTER) {
            attributes = BAD_CHARACTER
        } else {
            attributes = null
        }
        return pack(BACKGROUND, attributes)
    }

    companion object highlights {
        val BACKGROUND = createTextAttributesKey("PEBBLE_BACKGROUND",
                DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)

        val BAD_CHARACTER = createTextAttributesKey("PEBBLE_BAD_CHARACTER",
                HighlighterColors.BAD_CHARACTER)

        val BRACES = createTextAttributesKey("PEBBLE_BRACES",
                DefaultLanguageHighlighterColors.BRACES)

        val BRACKETS = createTextAttributesKey("PEBBLE_BRACKETS",
                DefaultLanguageHighlighterColors.BRACKETS)

        val COMMENT = createTextAttributesKey("PEBBLE_COMMENT",
                DefaultLanguageHighlighterColors.DOC_COMMENT)

        val DELIMITER = createTextAttributesKey("PEBBLE_DELIMITER",
                DefaultLanguageHighlighterColors.INSTANCE_FIELD)

        val IDENTIFIER = createTextAttributesKey("PEBBLE_IDENTIFIER",
                DefaultLanguageHighlighterColors.IDENTIFIER)

        val KEYWORDS = createTextAttributesKey("PEBBLE_KEYWORD",
                DefaultLanguageHighlighterColors.KEYWORD)

        val NUMBER = createTextAttributesKey("PEBBLE_NUMBER",
                DefaultLanguageHighlighterColors.NUMBER)

        val OPERATORS = createTextAttributesKey("PEBBLE_OPERATOR",
                DefaultLanguageHighlighterColors.OPERATION_SIGN)

        val PARENTHESES = createTextAttributesKey("PEBBLE_PARENTHESIS",
                DefaultLanguageHighlighterColors.PARENTHESES)

        val STRINGS = createTextAttributesKey("PEBBLE_STRING",
                DefaultLanguageHighlighterColors.STRING)
    }

    private val keywords = Arrays.asList(
            PebbleTypes.TRUE, PebbleTypes.FALSE, PebbleTypes.EQUALS,
            PebbleTypes.CONTAINS, PebbleTypes.AND, PebbleTypes.OR, PebbleTypes.NOT,
            PebbleTypes.IS, PebbleTypes.NULL
    )

    private val delimiters = Arrays.asList(
            PebbleTypes.TAG_OPEN, PebbleTypes.TAG_CLOSE,
            PebbleTypes.VAR_OPEN, PebbleTypes.VAR_CLOSE
    )

    private val braces = Arrays.asList(
            PebbleTypes.LBRACE, PebbleTypes.RBRACE
    )

    private val brackets = Arrays.asList(
            PebbleTypes.LBRACKET, PebbleTypes.RBRACKET
    )

    private val parens = Arrays.asList(
            PebbleTypes.LPAREN, PebbleTypes.RPAREN
    )

    private val operators = Arrays.asList(
            PebbleTypes.OP_PLUS, PebbleTypes.OP_MINUS,
            PebbleTypes.OP_MULT, PebbleTypes.OP_DIV,
            PebbleTypes.OP_MOD, PebbleTypes.OP_PIPE
    )

    private val strings = Arrays.asList(
            PebbleTypes.STRING, PebbleTypes.SINGLE_QUOTED_STRING
    )
}
