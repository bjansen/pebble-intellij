package com.github.bjansen.intellij.pebble.editor.completion

import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementPresentation
import java.io.File

class PebbleIdentifierCompletionTest : AbstractCompletionTest() {

    override fun getTestDataPath() = "src/test/resources/completion/identifiers"

    fun testCompletionOfSpringImplicitVariables() {
        myFixture.configureByFile("file1.peb")
        myFixture.addClass("package java.util; interface Map<K, V> {}")
        myFixture.addClass("package javax.servlet.http; class HttpServletRequest {}")
        myFixture.addClass("package javax.servlet.http; class HttpServletResponse {}")
        myFixture.addClass("package javax.servlet.http; class HttpSession {}")
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("beans", "request", "response", "session"))
    }

    fun testCompletionOfSpringImplicitFunctions() {
        myFixture.configureByFile("file1.peb")
        myFixture.complete(CompletionType.BASIC)

        val implicitFunctions = listOf("href", "message", "hasErrors", "hasGlobalErrors",
                "hasFieldErrors", "getAllErrors", "getGlobalErrors", "getFieldErrors")

        assertLookupsContain(implicitFunctions)
    }

    fun testCompletionOfGettersAsProperties() {
        myFixture.configureByFile("file2.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "method", "child"))
    }

    fun testCompletionOfChainedQualifiedMembers() {
        myFixture.configureByFile("file3.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "otherProperty", "thirdProperty", "method", "child"))
    }

    fun testCompletionOfFields() {
        myFixture.configureByFile("file4.peb")
        myFixture.addClass(File("$testDataPath/MyClass2.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("field1", "FIELD2"))
        assertLookupsDontContain(listOf("privateField"))
    }

    fun testCompletionOfMethodsFromFields() {
        myFixture.configureByFile("file5.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/MyClass2.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "method"))
    }

    fun testCompletionOfChainedQualifiedMembers2() {
        myFixture.configureByFile("file6.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/MyClass2.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)
        assertLookupsContain(listOf("property", "otherProperty", "thirdProperty", "method", "child"))
    }

    fun testCompletionOfDifferentKindsOfMethods() {
        myFixture.configureByFile("file7.peb")
        myFixture.addClass(File("pebble-intellij-test/src/foo/bar/SomeClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("pebble-intellij-test/src/foo/bar/SubClass.java").readText(Charsets.UTF_8))
        val completions = myFixture.complete(CompletionType.BASIC)

        // public fields
        assertLookupsContain(listOf("publicField"))

        // getters exposed as properties
        assertLookupsContain(listOf("property1", "property2", "property3"))

        // public methods, don't duplicate overridden methods
        assertLookupsContain(listOf("voidMethod", "intMethod", "integerMethod"))

        val signatures = completions.map {
            val presentation = LookupElementPresentation()
            it.renderElement(presentation)

            presentation.itemText +  presentation.tailText
        }

        assertContainsElements(signatures, "overloaded(String s)", "overloaded(Integer i)",
                "overloaded(String s, String s2)")

        // no constructors
        assertLookupsDontContain(listOf("SomeClass", "SubClass"))

        // no private, protected or package-private stuff
        assertLookupsDontContain(listOf("privateField", "protectedField", "packagePrivateField"))
        assertLookupsDontContain(listOf("property1Private", "property2Private", "property3Private"))
        assertLookupsDontContain(listOf("property1PackagePrivate", "property2PackagePrivate", "property3PackagePrivate"))
        assertLookupsDontContain(listOf("property1Protected", "property2Protected", "property3Protected"))
        assertLookupsDontContain(listOf("voidMethodProtected", "intMethodProtected", "integerMethodProtected"))
        assertLookupsDontContain(listOf("voidMethodPrivate", "intMethodPrivate", "integerMethodPrivate"))
        assertLookupsDontContain(listOf("voidMethodPackagePrivate", "intMethodPackagePrivate", "integerMethodPackagePrivate"))
    }

    /**
     * Checks that type parameters are correctly substituted in lookups for methods
     * declared in the class and its parent classes.
     */
    fun testCompletionWithGenerics() {
        myFixture.configureByFile("generics.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/List.java").readText(Charsets.UTF_8))

        val completions = myFixture.complete(CompletionType.BASIC)

        assertNotNull(completions)
        assertEquals(2, completions.size)

        val signatures = completions.map {
            val presentation = LookupElementPresentation()
            it.renderElement(presentation)

            presentation.itemText +  presentation.tailText
        }

        assertContainsElements(signatures, "add(MyClass element)", "addAt(int offset, MyClass element)")
    }

    fun testCompletionOfMacros() {
        myFixture.configureByFile("macros.peb")

        val completions = myFixture.complete(CompletionType.BASIC)

        assertNotNull(completions)

        val signatures = completions.map {
            val presentation = LookupElementPresentation()
            it.renderElement(presentation)

            presentation.itemText +  presentation.tailText
        }

        assertContainsElements(signatures, "hello(param1, param2, param3)")
    }

    fun testCompletionOfLoopInForTag() {
        myFixture.configureByFile("for.peb")
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("loop", "foo", "beforeLoop", "myVar", "myVar2"))
        assertLookupsDontContain(listOf("afterLoop"))
    }

    fun testScopes1() {
        myFixture.configureByFile("scopes1.peb")
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("loop", "foo", "foo2", "beforeLoop", "myVar", "myVar2"))
        assertLookupsDontContain(listOf("afterLoop"))
    }

    fun testCompletionOfForVariable() {
        myFixture.configureByFile("for2.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/List.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("method", "child", "otherProperty", "property", "thirdProperty"))
    }

    fun testCompletionOfForVariable_qualifier() {
        myFixture.configureByFile("for3.peb")
        myFixture.addClass(File("$testDataPath/MyClass.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/List.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("method", "child", "otherProperty", "property", "thirdProperty"))
    }

    fun testCompletionOfForVariable_field() {
        myFixture.configureByFile("for4.peb")
        myFixture.addClass(File("$testDataPath/Part.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/Car.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("label"))
    }

    fun testCompletionOfForVariable_subField() {
        myFixture.configureByFile("for5.peb")
        myFixture.addClass(File("$testDataPath/Part.java").readText(Charsets.UTF_8))
        myFixture.addClass(File("$testDataPath/Car.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("label"))
    }

    fun testCompletionOfScalaCollection() {
        myFixture.configureByFile("scala-iterable.peb")
        myFixture.addClass(File("$testDataPath/ScalaIterable.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("add"))
    }

    fun testCompletionOfStringMembers() {
        myFixture.configureByFile("literals-string.peb")
        myFixture.addClass(File("$testDataPath/String.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("toUpperCase"))
    }

    fun testCompletionOfBooleanMembers() {
        myFixture.configureByFile("literals-boolean.peb")
        myFixture.addClass(File("$testDataPath/Boolean.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("booleanValue"))
    }

    fun testCompletionOfNumberMembers() {
        myFixture.configureByFile("literals-number.peb")
        myFixture.addClass(File("$testDataPath/Number.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertLookupsContain(listOf("intValue"))
    }

    fun testCompletionOfNoneMembers() {
        myFixture.configureByText(PebbleFileType.INSTANCE, "{{ none.<caret> }}")
        myFixture.addClass(File("$testDataPath/Number.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertNoLookups()
    }

    fun testCompletionOfNullMembers() {
        myFixture.configureByText(PebbleFileType.INSTANCE, "{{ null.<caret> }}")
        myFixture.addClass(File("$testDataPath/Number.java").readText(Charsets.UTF_8))
        myFixture.complete(CompletionType.BASIC)

        assertNoLookups()
    }
}
