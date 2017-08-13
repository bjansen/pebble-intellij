package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

class PebbleCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(tokens[PebbleLexer.ID_NAME]),
                PebbleKeywordsCompletionProvider()
        )
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                PebbleBlockNameCompletionProvider()
        )
    }

}

class PebbleKeywordsCompletionProvider : CompletionProvider<CompletionParameters>() {

    val keywords = arrayOf("autoescape", "endautoescape", "block", "endblock", "cache", "endcache",
            "extends", "filter", "endfilter", "flush", "for", "endfor", "else", "if", "endif", "elseif",
            "import", "include", "macro", "endmacro", "parallel", "endparallel", "set", "verbatim", "endverbatim")

    val keywordLookupItems = keywords.map {
        LookupElementBuilder.create(it)
                .bold()
                .withInsertHandler { ctx, element ->
                    // try to invoke the matching live template if it exists
                    val tpl = findMatchingLiveTemplate(it)
                    val identifier = ctx.file.findElementAt(ctx.startOffset)
                    if (tpl != null && identifier != null) {
                        val openingDelimiter = PsiTreeUtil.prevVisibleLeaf(identifier)
                        if (openingDelimiter != null && openingDelimiter.node.elementType == tokens[PebbleLexer.TAG_OPEN]) {
                            ctx.editor.document.deleteString(openingDelimiter.textOffset, identifier.textOffset + identifier.textLength)
                            TemplateManager.getInstance(ctx.project)
                                    .startTemplate(ctx.editor, tpl)
                        }
                    }
                }
    }

    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?,
                                result: CompletionResultSet) {
        val el = parameters.position.originalElement
        val previousLeaf = PsiTreeUtil.prevVisibleLeaf(el)
        if (el.node.elementType == tokens[PebbleLexer.ID_NAME]
                && previousLeaf?.node?.elementType == tokens[PebbleLexer.TAG_OPEN]) {
            result.addAllElements(keywordLookupItems)
        }
    }

    fun findMatchingLiveTemplate(keyword: String): TemplateImpl? {
        val pebbleLiveTemplates = TemplateSettings.getInstance().templates.filter {
            it.groupName == "Pebble"
        }

        return pebbleLiveTemplates.firstOrNull {
            it.templateText.startsWith("{% $keyword ")
        }
    }
}
