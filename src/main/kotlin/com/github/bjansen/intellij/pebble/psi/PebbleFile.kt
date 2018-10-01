package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.ext.springExtension
import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.github.bjansen.intellij.pebble.utils.resourceUtil
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.Key
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

class PebbleFile constructor(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PebbleLanguage.INSTANCE) {
    private val key = Key.create<PsiClass>("PEBBLE_LOOP_CLASS")

    private val loopImplicitVariable: ImplicitVariable by lazy {
        val loopClass = resourceUtil.loadPsiClassFromFile("/implicitCode/Loop.java", key, project)
        val loopType = JavaPsiFacade.getInstance(project).elementFactory.createType(loopClass!!)
        PebbleImplicitVariable("loop", loopType, this, null)
    }

    override fun getFileType(): FileType {
        return PebbleFileType.INSTANCE
    }

    override fun toString(): String {
        return "Pebble file"
    }

    fun getImplicitVariables(location: PsiElement): List<PsiVariable> {
        val list = arrayListOf<PsiVariable>()

        if (inForTag(location)) {
            list.add(loopImplicitVariable)
        }
        list.addAll(findLocalImplicitVariables())
        list.addAll(springExtension.getImplicitVariables(this))

        return list
    }

    private fun inForTag(location: PsiElement): Boolean {
        var openedForLoops = 0

        object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement?) {
                if (element != null && element.textOffset < location.textOffset) {
                    if (element is PebbleTagDirective) {
                        if (element.getTagName() == "for") {
                            openedForLoops++
                        } else if (element.getTagName() == "endfor") {
                            openedForLoops--
                        }
                    }
                    super.visitElement(element)
                }
            }
        }.visitFile(this)

        return openedForLoops > 0
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
