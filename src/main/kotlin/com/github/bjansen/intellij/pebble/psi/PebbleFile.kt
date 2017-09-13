package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.ext.springExtension
import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.*

class PebbleFile constructor(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PebbleLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return PebbleFileType.INSTANCE
    }

    override fun toString(): String {
        return "Pebble file"
    }

    fun getImplicitVariables(): List<PsiVariable> {
        val list = arrayListOf<PsiVariable>()

        list.addAll(findLocalImplicitVariables())
        list.addAll(springExtension.getImplicitVariables(this))

        return list
    }

    fun getImplicitFunctions(): List<PsiNameIdentifierOwner> {
        val list = arrayListOf<PsiNameIdentifierOwner>()

        list.addAll(findLocalMacros())
        list.addAll(springExtension.getImplicitFunctions(this))

        return list
    }

    // TODO cache the result?
    private fun findLocalImplicitVariables(): List<ImplicitVariable> {
        val vars = arrayListOf<ImplicitVariable>()

        acceptChildren(object: PsiRecursiveElementWalkingVisitor() {
            override fun visitComment(comment: PsiComment?) {
                super.visitComment(comment)

                if (comment is PebbleComment) {
                    val implicitVar = PebbleComment.getImplicitVariable(comment)
                    if (implicitVar != null) {
                        vars.add(implicitVar)
                    }
                }
            }
        })

        return vars
    }

    private fun findLocalMacros(): List<PebbleMacroTag> {
        val macros = arrayListOf<PebbleMacroTag>()

        acceptChildren(object: PsiRecursiveElementWalkingVisitor() {
            override fun visitElement(element: PsiElement?) {
                super.visitElement(element)

                if (element is PebbleMacroTag) {
                    macros.add(element)
                }
            }
        })

        return macros
    }
}
