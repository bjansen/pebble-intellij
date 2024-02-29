package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class PebbleQuoteHandler
    : SimpleTokenSetQuoteHandler(tokens[PebbleLexer.STRING_START], tokens[PebbleLexer.DOUBLE_QUOTED_PLAIN_STRING], tokens[PebbleLexer.SINGLE_QUOTED_STRING])

