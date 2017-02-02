package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class PebbleBraceMatcher : PairedBraceMatcher {

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    override fun getPairs(): Array<out BracePair> {
        return arrayOf(
                BracePair(tokens[PebbleLexer.LPAREN], tokens[PebbleLexer.RPAREN], false),
                BracePair(tokens[PebbleLexer.LBRACE], tokens[PebbleLexer.RBRACE], false),
                BracePair(tokens[PebbleLexer.LBRACKET], tokens[PebbleLexer.RBRACKET], false)
        )
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }
}