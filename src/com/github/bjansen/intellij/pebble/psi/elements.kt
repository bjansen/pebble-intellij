package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode

class PebbleTagDirective(node: ASTNode) : ANTLRPsiNode(node), PsiNamedElement {

    override fun setName(name: String): PsiElement {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getName(): String? {
        val name = findChildByType<ANTLRPsiNode>(tokens[PebbleLexer.ID_NAME])
        if (name != null) {
            return name.text
        }
        return super.getName()
    }

    fun isVerbatim(): Boolean {
        return name?.equals("verbatim") ?: false
    }
}

class PebblePrintDirective(node: ASTNode) : ANTLRPsiNode(node)

class PebbleTemplate(node: ASTNode) : ANTLRPsiNode(node)
