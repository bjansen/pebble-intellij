package com.github.bjansen.intellij.pebble.codeInspection

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class UnknownVariableInspectionTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/resources/inspections"
    }

    fun testInspectionNothingToDo() {
        myFixture.configureByFile("addImplicitVariable-nothingToDo.peb")
        myFixture.enableInspections(UnknownVariableInspection::class.java)

        val highlights = myFixture.doHighlighting()

        assert(highlights.isEmpty())
    }

    fun testBlockNames() {
        myFixture.configureByFile("blockNames.peb")
        myFixture.enableInspections(UnknownVariableInspection::class.java)

        val highlights = myFixture.doHighlighting()
                .filter { it.severity != HighlightSeverity.INFORMATION }

        assert(highlights.isEmpty())
    }

    fun testMacroNamesAndParams() {
        myFixture.configureByFile("macroNamesAndParams.peb")
        myFixture.enableInspections(UnknownVariableInspection::class.java)

        val highlights = myFixture.doHighlighting()
                .filter { it.severity != HighlightSeverity.INFORMATION }

        assert(highlights.isEmpty())
    }

    fun testInspection() {
        myFixture.configureByFile("addImplicitVariable.peb")
        myFixture.enableInspections(UnknownVariableInspection::class.java)

        val highlights = myFixture.doHighlighting()

        assertEquals(1, highlights.size)

        val intention = highlights[0].quickFixActionRanges?.get(0)?.first?.action

        assertNotNull(intention)
        myFixture.launchAction(intention!!)
        myFixture.checkResultByFile("addImplicitVariable.after.peb")
    }

    fun testInspectionOnNestedTagsIssue54() {
        myFixture.configureByFile("addImplicitVariableNested.peb")
        myFixture.enableInspections(UnknownVariableInspection::class.java)

        val highlights = myFixture.doHighlighting()
            .filter { it.severity == HighlightSeverity.WARNING }

        assertEquals(3, highlights.size)

        val intention = highlights[2].quickFixActionRanges?.get(0)?.first?.action

        assertNotNull(intention)
        myFixture.launchAction(intention!!)
        myFixture.checkResultByFile("addImplicitVariableNested.after.peb")
    }
}
