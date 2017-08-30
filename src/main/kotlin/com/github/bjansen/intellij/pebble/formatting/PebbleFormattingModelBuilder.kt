package com.github.bjansen.intellij.pebble.formatting

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.formatting.Alignment
import com.intellij.formatting.Indent
import com.intellij.formatting.Wrap
import com.intellij.formatting.templateLanguages.*
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.xml.SyntheticBlock

/**
 * Creates formatting blocks for Pebble code and templated language code.
 */
class PebbleFormattingModelBuilder : TemplateLanguageFormattingModelBuilder() {
    override fun createTemplateLanguageBlock(node: ASTNode, wrap: Wrap?, alignment: Alignment?,
                                             foreignChildren: MutableList<DataLanguageBlockWrapper>?,
                                             codeStyleSettings: CodeStyleSettings)
            = PebbleBlock(this, codeStyleSettings, node, foreignChildren)
}

class PebbleBlock(blockFactory: TemplateLanguageBlockFactory, settings: CodeStyleSettings, node: ASTNode,
                  foreignChildren: MutableList<DataLanguageBlockWrapper>?)
    : TemplateLanguageBlock(blockFactory, settings, node, foreignChildren) {

    override fun getTemplateTextElementType()
            = PebbleParserDefinition.tokens[PebbleLexer.CONTENT]

    override fun getIndent(): Indent? {
        // If the parent is a block coming from another language, keep its child indent
        val foreignParent = getForeignBlockParent(true)
        if (foreignParent != null) {
            return Indent.getIndent(Indent.Type.NONE, 0, true, false)
        }
        return Indent.getNoneIndent()
    }

    /**
     * Returns this block's first "real" foreign block parent if it exists, and null otherwise.  (By "real" here, we mean that this method
     * skips SyntheticBlock blocks inserted by the template formatter)
     *
     * @param immediate Pass true to only check for an immediate foreign parent, false to look up the hierarchy.
     */
    private fun getForeignBlockParent(immediate: Boolean): DataLanguageBlockWrapper? {
        var foreignBlockParent: DataLanguageBlockWrapper? = null
        var parent: BlockWithParent? = parent

        while (parent != null) {
            if (parent is DataLanguageBlockWrapper && parent.original !is SyntheticBlock) {
                foreignBlockParent = parent
                break
            } else if (immediate && parent is PebbleBlock) {
                break
            }
            parent = parent.parent
        }

        return foreignBlockParent
    }
}
