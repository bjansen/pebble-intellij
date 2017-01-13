package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleTypes
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
                BracePair(PebbleTypes.LPAREN, PebbleTypes.RPAREN, false),
                BracePair(PebbleTypes.LBRACE, PebbleTypes.RBRACE, false),
                BracePair(PebbleTypes.LBRACKET, PebbleTypes.RBRACKET, false)
        )
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }
}