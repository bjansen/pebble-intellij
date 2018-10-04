package com.github.bjansen.intellij.pebble.psi

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

abstract class AbstractReferencesTest : LightCodeInsightFixtureTestCase() {
    protected fun initFile(vararg files: String): PebbleFile {
        val configuredFiles = myFixture.configureByFiles(*files)
        return configuredFiles[0] as PebbleFile
    }

    protected fun moveCaret(offset: Int) {
        myFixture.editor.caretModel.moveToOffset(offset)
    }
}
