package com.github.bjansen.intellij.pebble.editor.completion

import com.intellij.codeInsight.completion.CompletionType

class PebbleFiltersCompletionTest : AbstractCompletionTest() {
    override fun getTestDataPath() = "src/test/resources/completion/filters"

    fun testCompletionOfSpringImplicitFunctions() {
        myFixture.configureByFile("simple.peb")
        myFixture.complete(CompletionType.BASIC)

        val implicitFunctions = listOf("abbreviate", "abs", "capitalize", "date", "default")

        assertLookupsContain(implicitFunctions)
    }
}
