package com.github.bjansen.intellij.pebble.codeInspection

import com.google.common.io.Files
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import java.io.File

class UnknownAttributeInspectionTest : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/resources/inspections"
    }

    fun testInspection() {
        myFixture.configureByFile("unknownRefs.peb")
        myFixture.enableInspections(UnknownAttributeInspection::class.java)
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

        val highlights = myFixture.doHighlighting()

        assertEquals(5, highlights.size)

        // attributes of variable
        val first = highlights[0]
        assertEquals(74, first.actualStartOffset)
        assertEquals(81, first.actualEndOffset)
        assertEquals("Attribute [unknown] of [pebble.tests.MyClass2] does not exist or can not be accessed", first.description)

        val second = highlights[1]
        assertEquals(92, second.actualStartOffset)
        assertEquals(105, second.actualEndOffset)
        assertEquals("Attribute [unknownMethod] of [pebble.tests.MyClass2] does not exist or can not be accessed", second.description)

        // attribute of field
        val third = highlights[2]
        assertEquals(125, third.actualStartOffset)
        assertEquals(132, third.actualEndOffset)
        assertEquals("Attribute [unknown] of [pebble.tests.MyClass] does not exist or can not be accessed", third.description)

        // attribute of method
        val fourth = highlights[3]
        assertEquals(159, fourth.actualStartOffset)
        assertEquals(166, fourth.actualEndOffset)
        assertEquals("Attribute [unknown] of [pebble.tests.MyClass] does not exist or can not be accessed", fourth.description)

        // dereferencing a void type
        val fifth = highlights[4]
        assertEquals(190, fifth.actualStartOffset)
        assertEquals(197, fifth.actualEndOffset)
        assertEquals("Attempt to get attribute of null object", fifth.description)
    }

    fun testInspectionOnMap() {
        myFixture.configureByFile("mapRefs.peb")
        myFixture.enableInspections(UnknownAttributeInspection::class.java)
        myFixture.addClass(Files.toString(File("src/test/resources/inspections/Map.java"), Charsets.UTF_8))

        val highlights = myFixture.doHighlighting()

        assertEquals(0, highlights.size)
    }
}
