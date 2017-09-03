package com.github.bjansen.intellij.pebble.editor.completion

import com.google.common.io.Files
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import java.io.File

class PebbleIdentifierCompletionTest : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath() = "src/test/resources/completion/identifiers"

    fun testCompletionOfSpringImplicitVariables() {
        myFixture.configureByFile("file1.peb")
        myFixture.addClass("package java.util; interface Map<K, V> {}")
        myFixture.addClass("package javax.servlet.http; class HttpServletRequest {}")
        myFixture.addClass("package javax.servlet.http; class HttpServletResponse {}")
        myFixture.addClass("package javax.servlet.http; class HttpSession {}")
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(PebbleSpringCompletionProvider.implicitVariables.keys)
    }

    fun testCompletionOfSpringImplicitFunctions() {
        myFixture.configureByFile("file1.peb")
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(PebbleSpringCompletionProvider.implicitFunctions.asList())
    }

    fun testCompletionOfGettersAsProperties() {
        myFixture.configureByFile("file2.peb")
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass.java"), Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "method", "child"))
    }

    fun testCompletionOfChainedQualifiedMembers() {
        myFixture.configureByFile("file3.peb")
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass.java"), Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "otherProperty", "thirdProperty", "method", "child"))
    }

    fun testCompletionOfFields() {
        myFixture.configureByFile("file4.peb")
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass2.java"), Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("field1", "FIELD2"))
        assertLookupsDontContain(listOf("privateField"))
    }

    fun testCompletionOfMethodsFromFields() {
        myFixture.configureByFile("file5.peb")
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass2.java"), Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "method"))
    }

    fun testCompletionOfChainedQualifiedMembers2() {
        myFixture.configureByFile("file6.peb")
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(Files.toString(File("$testDataPath/MyClass2.java"), Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "otherProperty", "thirdProperty", "method", "child"))
    }

    private fun assertLookupsContain(elements: Collection<String>) {
        val lookups = myFixture.lookupElementStrings

        if (lookups != null) {
            assertContainsElements(lookups, elements)
        } else {
            assertNotNull(lookups)
        }
    }

    private fun assertLookupsDontContain(elements: Collection<String>) {
        val lookups = myFixture.lookupElementStrings

        if (lookups != null) {
            assertDoesntContain(lookups, elements)
        } else {
            assertNotNull(lookups)
        }
    }
}
