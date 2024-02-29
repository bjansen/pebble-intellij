package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.ext.SpringExtension
import com.github.bjansen.intellij.pebble.lang.PebbleCore
import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.github.bjansen.intellij.pebble.utils.ResourceUtil
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.Key
import com.intellij.psi.*
import com.intellij.psi.scope.PsiScopeProcessor
import org.antlr.intellij.adaptor.psi.ScopeNode

class PebbleFile constructor(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PebbleLanguage), ScopeNode {
    private val key = Key.create<PsiClass>("PEBBLE_LOOP_CLASS")

    val loopImplicitVariable: ImplicitVariable by lazy {
        val loopClass = ResourceUtil.loadPsiClassFromFile("/implicitCode/Loop.java", key, project)
        val loopType = JavaPsiFacade.getInstance(project).elementFactory.createType(loopClass!!)
        PebbleImplicitVariable("loop", loopType, this, null)
    }

    override fun getFileType(): FileType {
        return PebbleFileType.INSTANCE
    }

    override fun toString(): String {
        return "Pebble file"
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {

        SpringExtension.getImplicitVariables(this).forEach {
            if (!processor.execute(it, state)) {
                return false
            }
        }

        children.forEach {
            // For some reason, if a file starts with a comment it is not nested
            // in the PebbleTemplate
            if (it is PebbleComment) {
                it.processDeclarations(processor, state, lastParent, place)
            }
        }
        return true
    }

    fun getImplicitFunctions(): List<PsiNameIdentifierOwner> {
        val list = arrayListOf<PsiNameIdentifierOwner>()

        list.addAll(findLocalMacros())
        list.addAll(PebbleCore.getFunctions(project).map { it.source })
        list.addAll(SpringExtension.getImplicitFunctions(this))

        return list
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        return null
    }

    override fun getContext(): ScopeNode? {
        return null
    }

    // TODO cache the result?
    private fun findLocalImplicitVariables(): List<ImplicitVariable> {
        val vars = arrayListOf<ImplicitVariable>()

        acceptChildren(object: PsiRecursiveElementWalkingVisitor() {
            override fun visitComment(comment: PsiComment) {
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
            override fun visitElement(element: PsiElement) {
                super.visitElement(element)

                if (element is PebbleMacroTag) {
                    macros.add(element)
                }
            }
        })

        return macros
    }
}
