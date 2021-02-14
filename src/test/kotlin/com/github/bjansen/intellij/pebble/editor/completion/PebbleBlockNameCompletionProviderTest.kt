package com.github.bjansen.intellij.pebble.editor.completion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class PebbleBlockNameCompletionProviderTest : BasePlatformTestCase() {

    override fun getTestDataPath() = "src/test/resources/completion/blockNames"

    fun testCompletionOfBlocksInSameFile() {
        myFixture.configureByFile("file1.peb")
        myFixture.complete(CompletionType.BASIC)
        val lookups = myFixture.lookupElementStrings

        if (lookups != null) {
            assertContainsElements(lookups, listOf("b1", "b2", "b3"))
        } else {
            assertNotNull(lookups)
        }
    }

    fun testCompletionOfBlocksInIncludedFiles() {
        myFixture.configureByFiles("file2.peb", "file1.peb")
        myFixture.complete(CompletionType.BASIC)
        val lookups = myFixture.lookupElementStrings

        if (lookups != null) {
            assertContainsElements(lookups, listOf("b1", "b2", "b3", "b4"))
        } else {
            assertNotNull(lookups)
        }
    }
}
