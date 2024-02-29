package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class PebbleQuoteHandlerTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/resources/editor"

    fun testAutoclosingOfSingleQuote() {
        // Given
        myFixture.configureByText(PebbleFileType.INSTANCE, """{{ <caret> }}""")

        // When
        myFixture.type("'")

        // Then
        edt<Throwable> {
            assertEquals("{{ '' }}", myFixture.editor.document.text)
        }
    }

    fun testOverwritingOfSingleQuote() {
        // Given
        myFixture.configureByText(PebbleFileType.INSTANCE, """{{ '<caret>' }}""")

        // When
        myFixture.type("'")

        // Then
        edt<Throwable> {
            assertEquals("{{ '' }}", myFixture.editor.document.text)
        }
    }

    fun testAutoclosingOfDoubleQuote() {
        // Given
        myFixture.configureByText(PebbleFileType.INSTANCE, """{{ <caret> }}""")

        // When
        myFixture.type("\"")

        // Then
        edt<Throwable> {
            assertEquals("{{ \"\" }}", myFixture.editor.document.text)
        }
    }

    fun testOverwritingOfDoubleQuote() {
        // Given
        myFixture.configureByText(PebbleFileType.INSTANCE, """{{ "<caret>" }}""")

        // When
        myFixture.type("\"")

        // Then
        edt<Throwable> {
            assertEquals("{{ \"\" }}", myFixture.editor.document.text)
        }
    }

}
