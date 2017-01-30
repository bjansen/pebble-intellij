package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.parser.PebbleLexer
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode

class PebbleTagDirective(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {

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

class PebblePrintDirective(node: ASTNode) : ASTWrapperPsiElement(node) {

}

class PebbleTemplate(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun add(element: PsiElement): PsiElement {
        print("Add " + element.javaClass.name)
        return super.add(element)
    }
    override fun addAfter(element: PsiElement, anchor: PsiElement?): PsiElement {
        print("Add " + element.javaClass.name)
        return super.addAfter(element, anchor)
    }
}

