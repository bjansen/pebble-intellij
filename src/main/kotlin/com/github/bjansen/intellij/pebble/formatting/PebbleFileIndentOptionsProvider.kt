package com.github.bjansen.intellij.pebble.formatting

import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings.IndentOptions
import com.intellij.psi.codeStyle.FileIndentOptionsProvider
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider

class PebbleFileIndentOptionsProvider : FileIndentOptionsProvider() {
    override fun getIndentOptions(settings: CodeStyleSettings, file: PsiFile): IndentOptions? {
        if (file is PebbleFile) {
            val provider = file.viewProvider
            if (provider is TemplateLanguageFileViewProvider) {
                val language = provider.templateDataLanguage
                return settings.getCommonSettings(language).indentOptions
            }
        }
        return null
    }
}
