package com.github.bjansen.intellij.pebble.editor

import com.intellij.testFramework.fixtures.CompletionAutoPopupTestCase

class PebbleTypedHandlerTest : CompletionAutoPopupTestCase() {
    override fun getTestDataPath() = "src/test/resources/editor"

    fun testCompletionAfterOpeningTag() {
        // Given
        myFixture.configureByFile("openingTag.peb")

        // When
        myTester.typeWithPauses("%")

        // Then
        edt<Throwable> {
            assertEquals("some content {% \n", myFixture.editor.document.text)
            assertEquals("some content {% ".length, myFixture.editor.caretModel.offset)
            assertNotNull(myFixture.lookup)
            assertTrue(myFixture.lookup.items.isNotEmpty())
            assertContainsElements(myFixture.lookupElementStrings!!, "if", "for")
        }
    }

    fun testAutoclosingOfComment() {
        // Given
        myFixture.configureByFile("openingTag.peb")

        // When
        myFixture.type('#')

        // Then
        edt<Throwable> {
            assertEquals("some content {#  #}\n", myFixture.editor.document.text)
            assertEquals("some content {# ".length, myFixture.editor.caretModel.offset)
            assertNull(myFixture.lookup)
        }
    }

    fun testAutoclosingOfPrint() {
        // Given
        myFixture.configureByFile("openingTag.peb")

        // When
        myFixture.type('{')

        // Then
        edt<Throwable> {
            assertEquals("some content {{  }}\n", myFixture.editor.document.text)
            assertEquals("some content {{ ".length, myFixture.editor.caretModel.offset)
            assertNull(myFixture.lookup)
        }
    }

    fun testInsertionOfEndTag() {
        // Given
        myFixture.configureByFile("ifTag.peb")

        // When
        myFixture.type('}')

        // Then
        edt<Throwable> {
            assertEquals("some content {% if true %}{% endif %}\n", myFixture.editor.document.text)
            assertEquals("some content {% if true %}".length, myFixture.editor.caretModel.offset)
            assertNull(myFixture.lookup)
        }
    }
}
