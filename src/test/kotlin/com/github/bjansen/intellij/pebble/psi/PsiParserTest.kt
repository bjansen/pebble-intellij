package com.github.bjansen.intellij.pebble.psi

import com.intellij.testFramework.ParsingTestCase

class PsiParserTest : ParsingTestCase("", "peb", PebbleParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/resources/psi"
    }

    fun testIssue57() {
        doTest(true)
    }
}
