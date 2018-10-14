package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.jetbrains.adaptor.psi.ScopeNode

class PebbleMacroTag(node: ASTNode) : PebbleTagDirective(node), PsiNameIdentifierOwner, ScopeNode {

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setName(name: String): PsiElement {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getName(): String? {
        return nameIdentifier?.text ?: super.getName()
    }

    override fun getNameIdentifier(): PsiElement? {
        return findFunctionCall()?.firstChild
    }

    private fun findFunctionCall(): PsiElement? {
        return node.firstChildNode
                .findChildByType(rules[PebbleParser.RULE_expression])
                ?.findChildByType(rules[PebbleParser.RULE_function_call_expression])
                ?.psi
    }

    fun getParameterNames(): List<String> {
        val funcCall = findFunctionCall()
        
        if (funcCall != null) {
            val args = PsiTreeUtil.findChildOfType(funcCall, PebbleArgumentList::class.java)

            if (args != null) {
                return args.children
                        .filter {
                            it.node.elementType == rules[PebbleParser.RULE_expression]
                        }
                        .mapNotNull {
                            PsiTreeUtil.findChildOfType(it, PebbleIdentifier::class.java)?.name
                        }
            }
        }

        return emptyList()
    }
}
