package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings
import com.intellij.util.containers.ContainerUtil
import org.antlr.intellij.adaptor.lexer.TokenIElementType

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

    override fun getLanguages() = setOf(PebbleLanguage.INSTANCE, templateDataLanguage)

    override fun createFile(lang: Language): PsiFile? {
        return when (lang) {
            templateDataLanguage -> {
                val file = LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this) as PsiFileImpl
                file.contentElementType = getTemplateDataElementType(baseLanguage)
                file
            }
            baseLanguage -> LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this)
            else -> null
        }
    }

    companion object {
        private val templateDataToLang = ContainerUtil.newConcurrentMap<String, TemplateDataElementType>()
        val pebbleFragment = TokenIElementType(0, "PEBBLE_FRAGMENT", PebbleLanguage.INSTANCE)

        private fun getTemplateDataElementType(lang: Language): TemplateDataElementType {
            val result = templateDataToLang[lang.id]

            if (result != null) return result
            val content = PebbleParserDefinition.tokens[PebbleLexer.CONTENT]
            val created = TemplateDataElementType("PEBBLE_TEMPLATE_DATA", lang, content, pebbleFragment)
            val prevValue = templateDataToLang.putIfAbsent(lang.id, created)

            return prevValue ?: created
        }
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
