package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble._PebbleLexer
import com.intellij.lexer.FlexAdapter

class PebbleLexerAdapter : FlexAdapter(_PebbleLexer(null))
