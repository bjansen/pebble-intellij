package com.github.bjansen.intellij.pebble.lang

import com.intellij.lang.Language

class PebbleLanguage private constructor() : Language("Pebble") {
    companion object {
        val INSTANCE = PebbleLanguage()
    }
}
