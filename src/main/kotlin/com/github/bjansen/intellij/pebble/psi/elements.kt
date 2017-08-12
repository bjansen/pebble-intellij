package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.io.FileUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode
import java.util.*

class PebbleTagDirective(node: ASTNode) : ANTLRPsiNode(node), PsiNameIdentifierOwner {

    override fun setName(name: String): PsiElement {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getName(): String? {
        return nameIdentifier?.text ?: super.getName()
    }

    override fun getNameIdentifier(): PsiElement? {
        return node.firstChildNode.findChildByType(rules[PebbleParser.RULE_tagName])?.psi
    }

    override fun getReferences(): Array<PsiReference> {

        if (name in arrayOf("extends", "include", "import")) {
            val stringLiterals = ArrayList<PsiElement>()
            processChildren(this, {
                if (it.node.elementType == rules[PebbleParser.RULE_string_literal]) {
                    stringLiterals.add(it)
                }
            })

            val refs = ArrayList<PsiReference>()

            stringLiterals.forEach {
                val range = TextRange.from(it.textOffset - this.textOffset + 1, it.textLength - 2)
                val templateName = range.substring(text)

                // Resolves relative paths
                refs.addAll(FileReferenceSet(FileUtil.toSystemIndependentName(templateName),
                        this, range.startOffset, null, true).allReferences)

                // Resolves partial template names (prefix/suffix are automatically added)
                refs.add(TemplateReference(this, range))
            }

            return refs.toTypedArray()
        }

        return emptyArray()
    }

    private fun processChildren(element: PsiElement, processor: (PsiElement) -> Unit) {
        element.children.forEach {
            processor(it)
            processChildren(it, processor)
        }
    }

    fun isVerbatim(): Boolean {
        return name?.equals("verbatim") ?: false
    }
}

class PebblePrintDirective(node: ASTNode) : ANTLRPsiNode(node)

class PebbleTemplate(node: ASTNode) : ANTLRPsiNode(node)
