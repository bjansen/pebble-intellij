package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.psi.PebbleComment
import com.intellij.core.CoreASTFactory
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.tree.IElementType

class PebbleAstFactory : CoreASTFactory() {

    override fun createComment(type: IElementType, text: CharSequence): LeafElement {
        return PebbleComment(type, text)
    }
}