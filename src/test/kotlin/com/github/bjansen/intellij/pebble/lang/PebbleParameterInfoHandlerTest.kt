package com.github.bjansen.intellij.pebble.lang

import com.intellij.codeInsight.hint.ParameterInfoComponent
import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import com.intellij.testFramework.utils.parameterInfo.MockCreateParameterInfoContext
import com.intellij.testFramework.utils.parameterInfo.MockUpdateParameterInfoContext
import java.io.File

class PebbleParameterInfoHandlerTest : LightJavaCodeInsightFixtureTestCase() {

    private val myParameterInfoHandler = PebbleParameterInfoHandler()

    fun testUnresolved() {
        doTest(null)
    }

    fun testNoParam() {
        myFixture.addClass(File("src/test/resources/completion/identifiers/MyClass.java").readText(Charsets.UTF_8))
        doTest("<html>&lt;no parameters&gt;</html>")
    }

    fun testOneParam() {
        myFixture.addClass(File("src/test/resources/completion/identifiers/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("src/test/resources/completion/identifiers/List.java").readText(Charsets.UTF_8))
        doTest("<html><b>E element</b></html>")
    }

    fun testTwoParams() {
        myFixture.addClass(File("src/test/resources/completion/identifiers/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("src/test/resources/completion/identifiers/List.java").readText(Charsets.UTF_8))
        doTest("<html><b>int offset</b>, E element</html>")
    }

    fun testTwoParams2() {
        myFixture.addClass(File("src/test/resources/completion/identifiers/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("src/test/resources/completion/identifiers/List.java").readText(Charsets.UTF_8))
        doTest(1, "<html>int offset, <b>E element</b></html>")
    }

    private fun doTest(expectedPresentation: String?) {
        doTest(0, expectedPresentation)
    }

    private fun doTest(expectedParamIdx: Int, expectedPresentation: String?) {
        // Given
        myFixture.configureByFile(getTestName(true) + ".peb")
        // When
        val itemsToShow = getItemsToShow()
        val paramIdx = getHighlightedItem()
        val presentation = getPresentation(itemsToShow, paramIdx)
                // SDK 2018.3 introduced colors but we don't really care about them for our assertions
                ?.replace(" color=1d1d1d", "")

        // Then
        if (expectedPresentation != null) {
            assertEquals(1, itemsToShow!!.size)
            assertEquals(expectedParamIdx, paramIdx)
        } else {
            assertEquals(0, itemsToShow!!.size)
        }
        assertEquals(expectedPresentation, presentation)
    }

    private fun getItemsToShow(): Array<Any>? {
        val createCtx = MockCreateParameterInfoContext(myFixture.editor, myFixture.file)
        val psiElement = myParameterInfoHandler.findElementForParameterInfo(createCtx)
        assertNotNull(psiElement)
        myParameterInfoHandler.showParameterInfo(psiElement!!, createCtx)
        return createCtx.itemsToShow
    }

    private fun getHighlightedItem(): Int {
        val updateCtx = MockUpdateParameterInfoContext(myFixture.editor, myFixture.file)
        val psiElement = myParameterInfoHandler.findElementForUpdatingParameterInfo(updateCtx)
        assertNotNull(psiElement)
        myParameterInfoHandler.updateParameterInfo(psiElement!!, updateCtx)
        return updateCtx.currentParameter
    }

    private fun getPresentation(itemsToShow: Array<Any>?, paramIdx: Int): String? {
        val uiCtx = ParameterInfoComponent.createContext(itemsToShow, myFixture.editor, myParameterInfoHandler, paramIdx)
        return myParameterInfoHandler.updatePresentation(itemsToShow?.firstOrNull() as PsiElement?, uiCtx)
    }

    override fun getTestDataPath() = "src/test/resources/parameterInfo"
}
