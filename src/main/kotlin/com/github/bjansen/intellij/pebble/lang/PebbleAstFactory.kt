package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.psi.PebbleComment
import com.intellij.lang.DefaultASTFactoryImpl
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType

class PebbleAstFactory : DefaultASTFactoryImpl() {

    override fun createLeaf(type: IElementType, text: CharSequence): LeafElement {
        if (type == PebbleFileViewProvider.pebbleFragment) {
            return OuterLanguageElementImpl(type, text)
        }
        return super.createLeaf(type, text)
    }

    override fun createComment(type: IElementType, text: CharSequence): LeafElement {
        return PebbleComment(type, text)
    }
}
