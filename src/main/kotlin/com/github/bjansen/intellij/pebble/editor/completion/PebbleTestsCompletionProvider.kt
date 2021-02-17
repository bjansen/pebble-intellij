package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.lang.PebbleCore
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.util.ProcessingContext

class PebbleTestsCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters, context: ProcessingContext,
        result: CompletionResultSet
    ) {

        result.addAllElements(PebbleCore.getTests(parameters.originalFile.project).map {
            LookupElementBuilder.create(it.source, it.name)
                .withIcon(AllIcons.Nodes.Method)
        })
    }
}
