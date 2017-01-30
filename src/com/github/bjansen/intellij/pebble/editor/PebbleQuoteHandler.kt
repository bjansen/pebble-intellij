package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.parser.PebbleLexer
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class PebbleQuoteHandler
    : SimpleTokenSetQuoteHandler(tokens[PebbleLexer.STRING], tokens[PebbleLexer.SINGLE_QUOTED_STRING])

