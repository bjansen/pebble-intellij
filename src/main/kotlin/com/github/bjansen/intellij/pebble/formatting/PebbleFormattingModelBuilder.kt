package com.github.bjansen.intellij.pebble.formatting

import com.github.bjansen.intellij.pebble.lang.PebbleFileViewProvider
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.formatting.*
import com.intellij.formatting.templateLanguages.*
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.DocumentBasedFormattingModel
import com.intellij.psi.formatter.xml.SyntheticBlock
import com.intellij.psi.templateLanguages.SimpleTemplateLanguageFormattingModelBuilder

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
    ) = PebbleBlock(this, codeStyleSettings, node, foreignChildren)

    /**
     * We have to override [com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder.createModel]
     * since after we delegate to some templated languages, those languages (xml/html for sure, potentially others)
     * delegate right back to us to format the PebbleFileViewProvider.pebbleFragment token we tell them to ignore,
     * causing an stack-overflowing loop of polite format-delegation.
     */
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        val file: PsiFile = element.containingFile
        val node: ASTNode = element.node
        val rootBlock = if (node.elementType === PebbleFileViewProvider.pebbleFragment) {
            // If we're looking at a PebbleFileViewProvider.pebbleFragment element, then we've been invoked by our templated
            // language.  Make a dummy block to allow that formatter to continue
            return SimpleTemplateLanguageFormattingModelBuilder().createModel(element, settings)
        } else {
            getRootBlock(file, file.viewProvider, settings)
        }
        return DocumentBasedFormattingModel(rootBlock, element.project, settings, file.fileType, file)
    }

    /**
     * Do format my model!
     *
     * @return false all the time to tell the [com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder]
     * to not-not format our model (i.e. yes please!  Format away!)
     */
    override fun dontFormatMyModel(): Boolean {
        return false
    }
}

class PebbleBlock(
        blockFactory: TemplateLanguageBlockFactory,
        settings: CodeStyleSettings,
        node: ASTNode,
        foreignChildren: MutableList<DataLanguageBlockWrapper>?
) : TemplateLanguageBlock(blockFactory, settings, node, foreignChildren) {

    override fun getTemplateTextElementType() = tokens[PebbleLexer.CONTENT]

    override fun getIndent(): Indent? {
        // If the parent is a block coming from another language, keep its child indent
        val foreignParent = getForeignBlockParent(true)
        if (foreignParent != null) {
            return Indent.getNormalIndent()
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

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return ChildAttributes(Indent.getNormalIndent(), null)
    }
}
