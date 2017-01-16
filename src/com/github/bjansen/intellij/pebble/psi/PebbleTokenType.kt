package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class PebbleTokenType(@NonNls debugName: String)
    : IElementType(debugName, PebbleLanguage.INSTANCE)
