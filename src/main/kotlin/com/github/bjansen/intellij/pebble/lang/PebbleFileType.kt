package com.github.bjansen.intellij.pebble.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.TemplateLanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class PebbleFileType private constructor() : LanguageFileType(PebbleLanguage), TemplateLanguageFileType {

    override fun getName(): String {
        return "Pebble"
    }

    override fun getDescription(): String {
        return "Pebble template"
    }

    override fun getDefaultExtension(): String {
        return "peb"
    }

    override fun getIcon(): Icon? {
        return IconLoader.getIcon("/icons/pebble/logo.png")
    }

    companion object {
        @JvmField
        val INSTANCE = PebbleFileType()
    }
}
