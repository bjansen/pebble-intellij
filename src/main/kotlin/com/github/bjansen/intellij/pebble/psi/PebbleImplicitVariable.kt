package com.github.bjansen.intellij.pebble.psi

import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiType
import com.intellij.psi.impl.light.ImplicitVariableImpl

class PebbleImplicitVariable(name: String, type: PsiType, scope: PsiElement, val declaration: PsiElement? = null)
    : ImplicitVariableImpl(scope.manager, JavaPsiFacade.getElementFactory(scope.project).createIdentifier(name), type, false, scope) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PebbleImplicitVariable

        if (declaration != other.declaration) return false

        return true
    }

    override fun hashCode(): Int {
        return declaration?.hashCode() ?: 0
    }
}
