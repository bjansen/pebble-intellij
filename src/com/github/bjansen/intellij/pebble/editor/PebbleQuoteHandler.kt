package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleTypes
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class PebbleQuoteHandler : SimpleTokenSetQuoteHandler {

    constructor() : super(PebbleTypes.STRING, PebbleTypes.SINGLE_QUOTED_STRING) {

    }
}

