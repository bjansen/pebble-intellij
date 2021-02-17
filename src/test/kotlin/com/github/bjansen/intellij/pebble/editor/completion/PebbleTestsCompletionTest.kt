package com.github.bjansen.intellij.pebble.editor.completion

import com.intellij.codeInsight.completion.CompletionType

class PebbleTestsCompletionTest : AbstractCompletionTest() {
    override fun getTestDataPath() = "src/test/resources/completion/tests"

    fun testCompletionOfBuiltinTests() {
        myFixture.configureByFile("simple.peb")
        myFixture.complete(CompletionType.BASIC)

        val builtinTests = listOf("empty", "even", "map", "null", "odd", "iterable")
        assertLookupsContain(builtinTests)
    }
}
