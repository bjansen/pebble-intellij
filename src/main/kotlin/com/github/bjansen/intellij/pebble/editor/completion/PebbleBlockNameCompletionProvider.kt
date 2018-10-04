package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.github.bjansen.intellij.pebble.psi.directivesWithFileRefs
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import java.util.*

class PebbleBlockNameCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?, result: CompletionResultSet) {

        val el = parameters.position.originalElement
        val tag = PsiTreeUtil.getParentOfType(el, PebbleTagDirective::class.java)

        if (tag != null && tag.getTagName() in arrayOf("block", "endblock")) {
            val blockNames = HashSet<String>()
            val processedFiles = HashSet<PebbleFile>()

            processFile(el.containingFile, blockNames, processedFiles, result, parameters.originalPosition)
        }
    }

    private fun processFile(file: PsiFile, blockNames: MutableSet<String>, processedFiles: MutableSet<PebbleFile>,
                            result: CompletionResultSet, originatingTag: PsiElement?) {
        processChildren(file) {
            if (it is PebbleTagDirective) {
                if (it.getTagName() == "block") {
                    val blockName = PsiTreeUtil.nextVisibleLeaf(it.getTagNameElement()!!)
                    if (blockName != null && blockName.node.elementType == tokens[PebbleLexer.ID_NAME]
                        && blockName != originatingTag
                        && blockNames.add(blockName.text)) {
                        result.addElement(
                            LookupElementBuilder.create(blockName.text)
                                .withIcon(PebbleFileType.INSTANCE.icon)
                                .withTypeText("(${it.containingFile.name})", true)
                        )
                    }
                } else if (it.getTagName() in directivesWithFileRefs) {
                    it.references.forEach {
                        val resolved = it.resolve()
                        if (resolved is PebbleFile && processedFiles.add(resolved)) {
                            processFile(resolved, blockNames, processedFiles, result, originatingTag)
                        }
                    }
                }
            }
        }
    }

    private fun processChildren(element: PsiElement, processor: (PsiElement) -> Unit) {
        element.children.forEach {
            processor(it)
            processChildren(it, processor)
        }
    }
}
