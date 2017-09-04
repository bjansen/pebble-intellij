package com.github.bjansen.intellij.pebble.psi

import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiType
import com.intellij.psi.impl.light.ImplicitVariableImpl

class PebbleImplicitVariable(name: String, type: PsiType, scope: PsiElement, val declaration: PsiElement? = null)
    : ImplicitVariableImpl(scope.manager, JavaPsiFacade.getElementFactory(scope.project).createIdentifier(name), type, false, scope)