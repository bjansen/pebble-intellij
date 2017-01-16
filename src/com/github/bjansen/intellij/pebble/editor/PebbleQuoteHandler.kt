package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleTypes
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class PebbleQuoteHandler
    : SimpleTokenSetQuoteHandler(PebbleTypes.STRING, PebbleTypes.SINGLE_QUOTED_STRING)

