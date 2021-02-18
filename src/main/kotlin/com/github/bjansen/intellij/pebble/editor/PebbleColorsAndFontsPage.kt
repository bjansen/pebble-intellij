package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.PebbleBundle
import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class PebbleColorsAndFontsPage : ColorSettingsPage {

    private val attributeDescriptors = arrayOf(
            AttributesDescriptor(PebbleBundle.message("colors.background"), PebbleHighlighter.BACKGROUND),
            AttributesDescriptor(PebbleBundle.message("colors.bad.character"), PebbleHighlighter.BAD_CHARACTER),
            AttributesDescriptor(PebbleBundle.message("colors.braces"), PebbleHighlighter.BRACES),
            AttributesDescriptor(PebbleBundle.message("colors.brackets"), PebbleHighlighter.BRACKETS),
            AttributesDescriptor(PebbleBundle.message("colors.comment"), PebbleHighlighter.COMMENT),
            AttributesDescriptor(PebbleBundle.message("colors.delimiter"), PebbleHighlighter.DELIMITER),
            AttributesDescriptor(PebbleBundle.message("colors.identifier"), PebbleHighlighter.IDENTIFIER),
            AttributesDescriptor(PebbleBundle.message("colors.keywords"), PebbleHighlighter.KEYWORDS),
            AttributesDescriptor(PebbleBundle.message("colors.number"), PebbleHighlighter.NUMBER),
            AttributesDescriptor(PebbleBundle.message("colors.operators"), PebbleHighlighter.OPERATORS),
            AttributesDescriptor(PebbleBundle.message("colors.parentheses"), PebbleHighlighter.PARENTHESES),
            AttributesDescriptor(PebbleBundle.message("colors.strings"), PebbleHighlighter.STRINGS)
    )

    private val additionalHighlights = mutableMapOf(
            "kw" to PebbleHighlighter.KEYWORDS
    )

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> {
        return additionalHighlights
    }

    override fun getIcon(): Icon? {
        return PebbleFileType.INSTANCE.icon
    }

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor> {
        return attributeDescriptors
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return emptyArray()
    }

    override fun getDisplayName(): String {
        return "Pebble"
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return PebbleHighlighter()
    }

    override fun getDemoText() = PebbleLanguage.codeSample
}
