package com.github.bjansen.intellij.pebble.editor

import com.intellij.lang.Commenter

class PebbleCommenter : Commenter {
    override fun getLineCommentPrefix(): String? {
        return null
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return blockCommentPrefix
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return blockCommentSuffix
    }

    override fun getBlockCommentPrefix(): String? {
        return "{#"
    }

    override fun getBlockCommentSuffix(): String? {
        return "#}"
    }
}