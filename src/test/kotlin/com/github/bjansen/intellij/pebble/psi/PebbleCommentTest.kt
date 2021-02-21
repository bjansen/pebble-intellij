package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleComment.Companion.getImplicitVariable
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert

class PebbleCommentTest : BasePlatformTestCase(){

    fun testIssue25() {
        val comment = PsiElementFactory.createComment("{# @pebvariable name=\"contextVariable\" type=\"String\" #}", myFixture.project)
        Assert.assertNotNull(getImplicitVariable(comment))

        val commentWithEmptyName = PsiElementFactory.createComment("{# @pebvariable name=\"\" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithEmptyName))

        val commentWithLf = PsiElementFactory.createComment("{# @pebvariable name=\"\n\" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithLf))

        val commentWithSpace = PsiElementFactory.createComment("{# @pebvariable name=\" \" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithSpace))

        val commentWithKeyword = PsiElementFactory.createComment("{# @pebvariable name=\"void\" type=\"String\" #}", myFixture.project)
        Assert.assertNull(getImplicitVariable(commentWithKeyword))
    }
}
