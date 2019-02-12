package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.io.FileUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import com.intellij.psi.scope.PsiScopeProcessor
import org.antlr.intellij.adaptor.psi.ScopeNode
import java.util.*

val directivesWithFileRefs = arrayOf("extends", "include", "import")

open class PebbleTagDirective(node: ASTNode) : PebblePsiElement(node) {

    fun getTagName() = getTagNameElement()?.text

    fun getTagNameElement(): PsiElement? {
        return node.firstChildNode.findChildByType(rules[PebbleParser.RULE_tagName])?.psi
    }

    override fun getReferences(): Array<PsiReference> {

        if (getTagName() in directivesWithFileRefs) {
            val stringLiterals = ArrayList<PsiElement>()
            processChildren(this) {
                if (it.node.elementType == rules[PebbleParser.RULE_string_literal]) {
                    stringLiterals.add(it)
                }
            }

            val refs = ArrayList<PsiReference>()

            stringLiterals.forEach {
                val range = TextRange.from(it.textOffset - this.textOffset + 1, it.textLength - 2)
                val templateName = range.substring(text)

                // Resolves relative paths
                refs.addAll(
                    FileReferenceSet(
                        FileUtil.toSystemIndependentName(templateName),
                        this, range.startOffset, null, true
                    ).allReferences
                )

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
}

class PebblePrintDirective(node: ASTNode) : PebblePsiElement(node)

class PebbleTemplate(node: ASTNode) : PebblePsiElement(node), ScopeNode {
    override fun resolve(element: PsiNamedElement?): PsiElement? {
        return null
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        val stopElement = lastParent ?: place

        children.forEach {
            if (it.textOffset < stopElement.textOffset && (it is PebbleSetTag || it is PebbleComment)) {
                if (!processor.execute(it, state)) {
                    return false
                } else {
                    it.processDeclarations(processor, state, lastParent, place)
                }
            }
        }

        return true
    }
}

class PebbleIdentifier(node: ASTNode) : PebblePsiElement(node), PsiNamedElement {
    override fun setName(name: String): PsiElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getName(): String? {
        return node.text
    }

    override fun getReferences(): Array<PsiReference> {
        // Allows our PsiReferenceContributor to contribute their references
        return arrayOf(
            reference,
            *ReferenceProvidersRegistry.getReferencesFromProviders(this)
        ).requireNoNulls()
    }

    override fun getReference(): PsiReference? {
        return PebbleIdentifierReference(this, TextRange.from(0, node.textLength))
    }
}

class PebbleArgumentList(node: ASTNode) : PebblePsiElement(node)
