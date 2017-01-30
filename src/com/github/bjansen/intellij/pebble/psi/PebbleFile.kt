package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement

class PebbleFile constructor(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PebbleLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return PebbleFileType.INSTANCE
    }

    override fun add(element: PsiElement): PsiElement {
        print("Add " + element.javaClass.name)
        return super.add(element)
    }

    override fun addAfter(element: PsiElement, anchor: PsiElement?): PsiElement {
        print("Add " + element.javaClass.name)
        return super.addAfter(element, anchor)
    }

    override fun addRangeAfter(first: PsiElement?, last: PsiElement?, anchor: PsiElement?): PsiElement {
        print("Add " + first?.javaClass?.name)
        return super.addRangeAfter(first, last, anchor)
    }

    override fun toString(): String {
        return "Pebble file"
    }
}
