package com.github.bjansen.intellij.pebble.psi

import com.intellij.openapi.util.Key
import com.intellij.psi.scope.PsiScopeProcessor

interface PebbleScopeProcessor : PsiScopeProcessor {

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) = Unit

    override fun <T : Any?> getHint(hintKey: Key<T>): T? = null
}
