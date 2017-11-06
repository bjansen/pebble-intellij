package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleComment.Companion.getImplicitVariable
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.junit.Assert

class PebbleCommentTest : LightCodeInsightFixtureTestCase(){

    fun testIssue25() {
        val comment = psiElementFactory.createComment("{# @pebvariable name=\"contextVariable\" type=\"String\" #}", myFixture.project)
        Assert.assertNotNull(getImplicitVariable(comment))

        val commentWithEmptyName = psiElementFactory.createComment("{# @pebvariable name=\"\" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithEmptyName))

        val commentWithLf = psiElementFactory.createComment("{# @pebvariable name=\"\n\" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithLf))

        val commentWithSpace = psiElementFactory.createComment("{# @pebvariable name=\" \" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithSpace))

        val commentWithKeyword = psiElementFactory.createComment("{# @pebvariable name=\"void\" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithKeyword))
    }
}
