package com.github.bjansen.intellij.pebble.formatting

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.formatting.Alignment
import com.intellij.formatting.Indent
import com.intellij.formatting.Wrap
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock
import com.intellij.formatting.templateLanguages.TemplateLanguageBlockFactory
import com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings

/**
 * Creates formatting blocks for Pebble code and templated language code.
 */
class PebbleFormattingModelBuilder : TemplateLanguageFormattingModelBuilder() {
    override fun createTemplateLanguageBlock(
        node: ASTNode,
        wrap: Wrap?,
        alignment: Alignment?,
        foreignChildren: MutableList<DataLanguageBlockWrapper>?,
        codeStyleSettings: CodeStyleSettings
    ): PebbleBlock {
        return PebbleBlock(node, wrap, alignment, codeStyleSettings, this, foreignChildren)
    }
}

class PebbleBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    settings: CodeStyleSettings,
    blockFactory: TemplateLanguageBlockFactory,
    foreignChildren: MutableList<DataLanguageBlockWrapper>?
) : TemplateLanguageBlock(node, wrap, alignment, blockFactory, settings, foreignChildren) {

    override fun getTemplateTextElementType() = tokens[PebbleLexer.CONTENT]

    override fun getIndent(): Indent? {
        // ignore whitespace
        if (myNode.text.trim().isEmpty()) {
            return Indent.getNoneIndent()
        }

        val parent = parent

        if (parent is DataLanguageBlockWrapper) {
            return Indent.getNormalIndent()
        } else if (parent is PebbleBlock) {
            if (parent.node.lastChildNode == node) {
                // we're the parent's end tag, don't indent
                return Indent.getNoneIndent()
            }

            for (subBlock in parent.subBlocks) {
                if (subBlock is DataLanguageBlockWrapper && subBlock.textRange.startOffset < textRange.startOffset) {
                    // We're next to a data language block, so we use its indent
                    return subBlock.indent
                }
            }

            if (node.psi is PebbleTagDirective) {
                // we're an opening tag, indent
                return Indent.getNormalIndent()
            }
        }

        return Indent.getNoneIndent()
    }

    override fun getChildIndent(): Indent? {
        return Indent.getNormalIndent()
    }
}
