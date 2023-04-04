package com.github.bjansen.intellij.pebble.formatting

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.github.bjansen.intellij.pebble.psi.PebbleTemplate
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.formatting.ASTBlock
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

    override fun shouldBuildBlockFor(childNode: ASTNode?): Boolean {
        // We want to indent non-PebbleBlock child nodes too
        return true
    }

    override fun getIndent(): Indent? {
        // ignore whitespace
        if (myNode.text.trim().isEmpty()) {
            return Indent.getNoneIndent()
        }

        val parent = parent

        if (parent is PebbleBlock || parent is DataLanguageBlockWrapper) {
            val parentNode = (parent as ASTBlock).node ?: return Indent.getNoneIndent()

            if (parentNode.psi is PebbleTemplate) {
                // Do not indent root nodes
                return Indent.getNoneIndent()
            }

            if (parent is PebbleBlock && (parentNode.firstChildNode == node || parentNode.lastChildNode == node)) {
                // Do not the start/end tags of a block
                return Indent.getNoneIndent()
            }

            // Indent contents of a block
            return Indent.getNormalIndent()
        }

        return Indent.getNoneIndent()
    }

    override fun getChildIndent(): Indent {
        return if (node.psi is PebbleTagDirective) {
            Indent.getNormalIndent(true)
        } else {
            Indent.getNoneIndent()
        }
    }
}
