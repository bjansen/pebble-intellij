package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.lang.PebbleCore
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.util.ProcessingContext

class PebbleFiltersCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters, context: ProcessingContext,
        result: CompletionResultSet
    ) {

        result.addAllElements(PebbleCore.getFilters(parameters.originalFile.project).map {
            val insertHandler =
                if (it.parameters.isEmpty()) null
                else ParenthesesInsertHandler.WITH_PARAMETERS

            val tailText =
                if (it.parameters.isEmpty()) null
                else it.parameters.keys.joinToString(", ", "(", ")")

            LookupElementBuilder.create(it.name)
                .withTailText(tailText)
                .withIcon(AllIcons.Nodes.Method)
                .withInsertHandler(insertHandler)
        })
    }

}
