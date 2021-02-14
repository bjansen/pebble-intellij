package com.github.bjansen.intellij.pebble.editor.completion

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

abstract class AbstractCompletionTest : LightJavaCodeInsightFixtureTestCase() {

    protected fun assertLookupsContain(elements: Collection<String>) {
        val lookups = myFixture.lookupElementStrings

        if (lookups != null) {
            assertContainsElements(lookups, elements)
        } else {
            assertNotNull(lookups)
        }
    }

    protected fun assertLookupsDontContain(elements: Collection<String>) {
        val lookups = myFixture.lookupElementStrings

        if (lookups != null) {
            assertDoesntContain(lookups, elements)
        } else {
            assertNotNull(lookups)
        }
    }
}
