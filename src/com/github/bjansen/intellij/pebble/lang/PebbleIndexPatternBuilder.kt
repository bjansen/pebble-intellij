package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.parser.PebbleLexer
import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.createLexer
import com.intellij.lexer.Lexer
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.search.IndexPatternBuilder
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

/**
 * Allows finding patterns like TODOs and FIXMEs in comments.
 */
class PebbleIndexPatternBuilder : IndexPatternBuilder {
    override fun getCommentStartDelta(tokenType: IElementType?): Int {
        return 0
    }

    override fun getIndexingLexer(file: PsiFile): Lexer? {
        if (file is PebbleFile) {
            return createLexer(null, file.project)
        }
        return null
    }

    override fun getCommentTokenSet(file: PsiFile): TokenSet? {
        return TokenSet.create(tokens[PebbleLexer.COMMENT])
    }

    override fun getCommentEndDelta(tokenType: IElementType?): Int {
        return "#}".length
    }
}