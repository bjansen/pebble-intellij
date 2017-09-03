package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.editor.completion.PebbleSpringCompletionProvider
import com.github.bjansen.intellij.pebble.lang.PebbleFileType
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.ImplicitVariable
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiRecursiveElementWalkingVisitor

class PebbleFile constructor(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PebbleLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return PebbleFileType.INSTANCE
    }

    override fun toString(): String {
        return "Pebble file"
    }

    fun getImplicitVariables(): List<ImplicitVariable> {
        val list = arrayListOf<ImplicitVariable>()

        list.addAll(findLocalImplicitVariables())
        list.addAll(PebbleSpringCompletionProvider.getImplicitVariables(this))

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
}
