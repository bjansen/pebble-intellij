package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.github.bjansen.intellij.pebble.psi.validTagNames
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.psi.tree.TokenSet

class PebbleFoldingBuilder : CustomFoldingBuilder() {

    private val tagsWithNames = TokenSet.create(
        rules[PebbleParser.RULE_genericTag],
        rules[PebbleParser.RULE_importTag],
        rules[PebbleParser.RULE_fromImportTag]
    )

    override fun isRegionCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }

    override fun buildLanguageFoldRegions(descriptors: MutableList<FoldingDescriptor>,
                                          root: PsiElement, document: Document, quick: Boolean) {
        root.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is PebbleTagDirective && !element.textRange.isEmpty) {
                    descriptors.add(FoldingDescriptor(element, element.textRange))
                }
                super.visitElement(element)
            }
        })
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String {
        val name = node.findChildByType(tagsWithNames)?.findChildByType(validTagNames)
        if (name != null) {
            return "{% ${name.text} %}"
        }

        return "..." // TODO
    }
}
