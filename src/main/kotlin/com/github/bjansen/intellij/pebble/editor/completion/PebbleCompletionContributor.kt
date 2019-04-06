package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns.psiElement

class PebbleCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC,
                psiElement(PebbleParserDefinition.tokens[PebbleLexer.ID_NAME])
                        .afterLeaf(psiElement(PebbleParserDefinition.tokens[PebbleLexer.TAG_OPEN])),
                PebbleKeywordsCompletionProvider()
        )
        extend(CompletionType.BASIC,
                psiElement(PebbleParserDefinition.tokens[PebbleLexer.ID_NAME])
                        .afterLeaf(psiElement(PebbleParserDefinition.tokens[PebbleLexer.OP_PIPE])),
                PebbleFiltersCompletionProvider()
        )
        extend(CompletionType.BASIC,
                psiElement(),
                PebbleBlockNameCompletionProvider()
        )
    }
}
