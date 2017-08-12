package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class PebbleFile constructor(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PebbleLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return PebbleFileType.INSTANCE
    }

    override fun toString(): String {
        return "Pebble file"
    }
}
