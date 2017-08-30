package com.github.bjansen.intellij.pebble.lang

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings

class PebbleFileViewProviderFactory : FileViewProviderFactory {
    override fun createFileViewProvider(file: VirtualFile, language: Language?, manager: PsiManager, eventSystemEnabled: Boolean): FileViewProvider {
        return PebbleFileViewProvider(manager, file, eventSystemEnabled)
    }
}

class PebbleFileViewProvider(manager: PsiManager, private val file: VirtualFile, eventSystemEnabled: Boolean,
                             private val myTemplateLanguage: Language = computeTemplateLanguage(manager, file))
    : MultiplePsiFilesPerDocumentFileViewProvider(manager, file, eventSystemEnabled),
        ConfigurableTemplateLanguageFileViewProvider {

    override fun getBaseLanguage() = PebbleLanguage.INSTANCE

    override fun cloneInner(file: VirtualFile) = PebbleFileViewProvider(manager, file, false, myTemplateLanguage)

    override fun getTemplateDataLanguage() = myTemplateLanguage

    override fun supportsIncrementalReparse(rootLanguage: Language): Boolean {
        return super.supportsIncrementalReparse(rootLanguage)
    }

    override fun getLanguages() = setOf(PebbleLanguage.INSTANCE, templateDataLanguage)

    override fun createFile(lang: Language): PsiFile? {
        return when (lang) {
            templateDataLanguage,
            baseLanguage -> LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this)
            else -> null
        }
    }

    companion object {
        private fun computeTemplateLanguage(manager: PsiManager, file: VirtualFile): Language {
            val mappings = TemplateDataLanguageMappings.getInstance(manager.project)
            val dataLang = mappings.getMapping(file)
                    ?: PlainTextLanguage.INSTANCE
            val substituteLang = LanguageSubstitutors.INSTANCE.substituteLanguage(dataLang, file, manager.project)

            if (TemplateDataLanguageMappings.getTemplateableLanguages().contains(substituteLang)) {
                return substituteLang
            }

            return dataLang
        }
    }
}
