package com.github.bjansen.intellij.pebble.psi

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import org.apache.commons.io.FileUtils
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
        myFixture.addClass(FileUtils.readFileToString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))

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
        myFixture.addClass(FileUtils.readFileToString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(FileUtils.readFileToString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

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
        myFixture.addClass(FileUtils.readFileToString(File("src/test/resources/completion/identifiers/MyClass.java"), Charsets.UTF_8))
        myFixture.addClass(FileUtils.readFileToString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

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
        myFixture.addClass(FileUtils.readFileToString(File("src/test/resources/completion/identifiers/MyClass2.java"), Charsets.UTF_8))

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

    fun testReferenceToBlock() {
        initFile("block.peb")

        moveCaret(75)

        val resolved = resolveRefAtCaret()
        if (resolved != null) {
            assert(resolved is PebbleBlockTag)
            assert((resolved as PebbleBlockTag).name == "hello")
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testReferenceToLoop() {
        initFile("loop.peb")

        moveCaret(30)

        val resolved = resolveRefAtCaret()
        if (resolved != null) {
            assert(resolved is PebbleImplicitVariable)
            assert((resolved as PebbleImplicitVariable).type.presentableText == "Loop")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(35)

        val resolved2 = resolveRefAtCaret()
        if (resolved2 != null) {
            assert(resolved2 is PsiField)
            assert((resolved2 as PsiField).name == "index")
        } else {
            fail("Reference resolved to nothing")
        }

        moveCaret(60)

        val resolved3 = resolveRefAtCaret()
        if (resolved3 != null) {
            fail("Expected 'loop' to resolve to nothing outside of 'for' loop")
        }
    }

    fun testReferenceToSet() {
        initFile("set.peb")

        moveCaret(30)

        val resolved = resolveRefAtCaret()
        if (resolved != null) {
            assert(resolved is PebbleSetTag)
            assert((resolved as PebbleSetTag).name == "myVar")
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testReferenceToFilter() {
        initFile("filters.peb")

        moveCaret(23)

        val resolved = resolveRefAtCaret()
        assertNull("Expected reference to resolve to nothing", resolved)

        moveCaret(60)

        val resolved2 = resolveRefAtCaret()
        if (resolved2 != null) {
            assert(resolved2 is PsiMethod)
            assert((resolved2 as PsiMethod).name == "lower")
        } else {
            fail("Reference resolved to nothing")
        }

       moveCaret(70)

        val resolved3 = resolveRefAtCaret()
        if (resolved3 != null) {
            assert(resolved3 is PsiMethod)
            assert((resolved3 as PsiMethod).name == "capitalize")
        } else {
            fail("Reference resolved to nothing")
        }
    }

    fun testReferenceToForVar() {
        initFile("for.peb")

        moveCaret(32)

        val resolved = resolveRefAtCaret()

        if (resolved != null) {
            assert(resolved is PebbleInVariable)
            assert((resolved as PebbleInVariable).name == "i")
        } else {
            fail("Reference resolved to nothing")
        }
    }
}
