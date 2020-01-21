package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.findMembersByName
import com.github.bjansen.pebble.parser.PebbleLexer
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.psi.*

class ExpressionTypeVisitor : PsiRecursiveElementVisitor(false) {
    var type: PsiType? = null

    override fun visitElement(element: PsiElement?) {
        if (element == null) {
            return
        }

        if (element.node.elementType == rules[PebbleParser.RULE_qualified_expression]) {
            type = typeOfQualifiedExpression(element)
        }

        if (element is PebbleIdentifier) {
            type = typeOfIdentifier(element)
            return
        }

        super.visitElement(element)
    }

    private fun typeOfIdentifier(element: PebbleIdentifier): PsiType? {
        return typeOf(element.reference?.resolve())
    }

    private fun typeOfQualifiedExpression(element: PsiElement): PsiType? {
        var qualifierType:PsiType? = null

        for (child in element.children) {
            if (qualifierType == null) {// leftmost qualifier
                qualifierType = if (child is PebbleIdentifier) {
                    typeOfIdentifier(child)
                } else {
                    typeOfIdentifier(child.firstChild as PebbleIdentifier)
                }
            } else if (child.node.elementType == tokens[PebbleLexer.OP_MEMBER]) {
                continue
            } else {
                if (qualifierType is PsiClassType) {
                    val candidates = findMembersByName(qualifierType.resolve(), if (child is PebbleIdentifier) child.text else child.firstChild.text)
                    qualifierType = typeOf(candidates.firstOrNull())
                }
            }

            if (qualifierType == null) {
                break // We can't possibly determine the type of what's after the current child
            }
        }

        return qualifierType
    }

    private fun typeOf(element: PsiElement?): PsiType? {
        if (element is PebbleComment) {
            return PebbleComment.getImplicitVariable(element)?.type
        } else if (element is PsiMethod) {
            return element.returnType
        } else if (element is PebbleImplicitVariable) {
            return element.type
        }

        return null
    }
}
