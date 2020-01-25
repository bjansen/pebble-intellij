package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.io.FileUtil
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.InheritanceUtil
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

class PebbleInVariable(node: ASTNode) : PebblePsiElement(node), PsiNamedElement {

    override fun getName(): String? {
        return node.text
    }

    fun getType(): PsiType? {
        val expr = node.treeParent.findChildByType(rules[PebbleParser.RULE_expression], node)

        return if (expr != null) inferVariableType(expr.psi) else null
    }

    override fun setName(name: String): PsiElement {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun inferVariableType(iterableExpression: PsiElement): PsiType {
        val visitor = ExpressionTypeVisitor()
        iterableExpression.accept(visitor)

        val type = visitor.type

        if (type != null) {
            var iteratedType: PsiType? = null

            InheritanceUtil.processSuperTypes(type, true) {
                if (it.canonicalText.startsWith("java.lang.Iterable")
                    && it is PsiClassType && it.parameters.isNotEmpty()) {
                    iteratedType = it.parameters[0]
                }

                true
            }

            if (iteratedType != null) {
                return iteratedType as PsiType
            }
        }

        return PsiType.getJavaLangObject(
            PsiManager.getInstance(containingFile.project),
            GlobalSearchScope.allScope(containingFile.project)
        )
    }
}

class PebbleArgumentList(node: ASTNode) : PebblePsiElement(node)
