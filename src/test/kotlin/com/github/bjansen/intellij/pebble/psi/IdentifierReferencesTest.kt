package com.github.bjansen.intellij.pebble.psi

import com.google.common.io.Files
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import java.io.File

class IdentifierReferencesTest : AbstractReferencesTest() {

    override fun getTestDataPath(): String = "src/test/resources/references"

    private fun resolveRefAtCaret(): PsiElement? {
        val elementAtCaret = file.findElementAt(myFixture.caretOffset)

        if (elementAtCaret != null) {
            val ref = elementAtCaret.parent.reference

            if (ref != null) {
                return ref.resolve()
            } else {
                fail("No reference at caret")
            }
        } else {
            fail("No element at caret")
        }

        return null
    }

    fun testReferenceToImplicitVar() {
        initFile("identifierRefs.peb")

        moveCaret(70)

        val resolved = resolveRefAtCaret()
        if (resolved != null) {
            assert(resolved is PsiComment)
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testReferenceToGetter() {
        initFile("identifierRefs.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))

        moveCaret(85)

        val resolvedGet = resolveRefAtCaret()
        if (resolvedGet != null) {
            assert(resolvedGet is PsiMethod)
            assert((resolvedGet as PsiMethod).name == "getProperty")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(265)

        val resolvedHas = resolveRefAtCaret()
        if (resolvedHas != null) {
            assert(resolvedHas is PsiMethod)
            assert((resolvedHas as PsiMethod).name == "hasOtherProperty")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(290)

        val resolvedIs = resolveRefAtCaret()
        if (resolvedIs != null) {
            assert(resolvedIs is PsiMethod)
            assert((resolvedIs as PsiMethod).name == "isThirdProperty")
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testReferenceToNestedGetter() {
        initFile("identifierRefs.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

        moveCaret(110)

        val resolved = resolveRefAtCaret()
        if (resolved != null) {
            assert(resolved is PsiMethod)
            assert((resolved as PsiMethod).name == "getChild")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(320)

        val resolved2 = resolveRefAtCaret()
        if (resolved2 != null) {
            assert(resolved2 is PsiMethod)
            assert((resolved2 as PsiMethod).name == "method")
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testChainedCallsReference() {
        initFile("identifierRefs.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

        moveCaret(355)

        val resolved1 = resolveRefAtCaret()
        if (resolved1 != null) {
            assert(resolved1 is PsiMethod)
            assert((resolved1 as PsiMethod).name == "getProperty")
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testReferenceToFields() {
        initFile("identifierRefs.peb")
        myFixture.addClass(Files.toString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

        moveCaret(190)

        val resolved1 = resolveRefAtCaret()
        if (resolved1 != null) {
            assert(resolved1 is PsiField)
            assert((resolved1 as PsiField).name == "field1")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(205)

        val resolved2 = resolveRefAtCaret()
        if (resolved2 != null) {
            assert(resolved2 is PsiField)
            assert((resolved2 as PsiField).name == "FIELD2")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(220)

        val resolved3 = resolveRefAtCaret()
        if (resolved3 != null) {
            fail("Expected 'field2' to resolve to nothing")
        }

        moveCaret(240)

        val resolved4 = resolveRefAtCaret()
        if (resolved4 != null) {
            fail("Expected 'privateField' to resolve to nothing")
        }
    }

    fun testReferenceToMacro() {
        initFile("macros.peb")

        moveCaret(42)

        val resolved = resolveRefAtCaret()
        if (resolved != null) {
            assert(resolved is PebbleMacroTag)
            assert((resolved as PebbleMacroTag).name == "hello")
        } else {
            fail("Reference resolved to nothing")
        }
    }
}
