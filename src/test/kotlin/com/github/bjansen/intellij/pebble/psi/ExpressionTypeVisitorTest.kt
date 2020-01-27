package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.pebble.parser.PebbleParser
import com.google.common.io.Files
import com.intellij.openapi.util.Condition
import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import junit.framework.TestCase
import java.io.File

class ExpressionTypeVisitorTest : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath() = "src/test/resources/psi/ExpressionTypeVisitor"

    fun test_evaluate_type_of_implicit_variable() {
        myFixture.configureByFile("implicitVariables.peb")

        val expression = findExpression(58)
        val visitor = ExpressionTypeVisitor()

        visitor.visitElement(expression!!)
        assertEquals("java.lang.String", visitor.type?.canonicalText)

        visitor.visitElement(findExpression(140)!!)
        assertEquals("java.util.List<java.lang.String>", visitor.type?.canonicalText)
    }

    fun test_evaluate_type_of_for_implicit_var() {
        myFixture.configureByFile("forImplicitVar.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/psi/ExpressionTypeVisitor/List.java"), Charsets.UTF_8))

        val visitor = ExpressionTypeVisitor()

        val expr = findExpression(85)

        if (expr != null) {
            val inExpression = expr.firstChild
            val inExpressionRhs = inExpression.lastChild
            visitor.visitElement(inExpressionRhs)
            assertEquals("pebble.tests.List<java.lang.String>", visitor.type?.canonicalText)
        } else {
            TestCase.fail("Expected an expression at given offset")
        }
    }

    fun test_evaluate_type_of_for_implicit_qualified_var() {
        myFixture.configureByFile("forImplicitVar.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/psi/ExpressionTypeVisitor/List.java"), Charsets.UTF_8))

        val visitor = ExpressionTypeVisitor()

        val expr = findExpression(155)

        if (expr != null) {
            val inExpression = expr.firstChild
            val inExpressionRhs = inExpression.lastChild
            visitor.visitElement(inExpressionRhs)

            // TODO figure out why it doesn't resolve to java.util.Iterator<java.lang.String>
            assertEquals("java.util.Iterator<E>", visitor.type?.canonicalText)
        } else {
            TestCase.fail("Expected an expression at given offset")
        }
    }

    fun test_evaluate_type_of_method_call() {
        myFixture.configureByFile("methodCall.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))

        val visitor = ExpressionTypeVisitor()

        visitor.visitElement(findExpression(64)!!)
        assertEquals("String", visitor.type?.canonicalText)

        visitor.visitElement(findExpression(84)!!)
        assertEquals("boolean", visitor.type?.canonicalText)
    }

    private fun findExpression(offset: Int) = findLastParent(
            myFixture.file.findElementAt(offset),
            Condition { it.node?.elementType == PebbleParserDefinition.rules[PebbleParser.RULE_expression] }
    )

    private fun findLastParent(element: PsiElement?, condition: Condition<in PsiElement>): PsiElement? {
        var parent = element
        var lastMatchingParent: PsiElement? = null

        while (parent != null) {
            if (condition.value(parent)) {
                lastMatchingParent = parent
            }
            parent = parent.parent
        }
        return lastMatchingParent
    }

}
