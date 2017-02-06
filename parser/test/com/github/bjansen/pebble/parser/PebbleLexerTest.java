package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Test;

public class PebbleLexerTest extends AbstractLexerTest {

    @Test
    public void testWhitespaceControlModifiers() {
        lexFile("test-resources/lexer/pebble/ws-ctrl-modifier.peb");
    }

    @Override
    PebbleLexer createLexer(String text) {
        return new PebbleLexer(new ANTLRInputStream(text));
    }
}
