package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Test;

public class PebbleLexerTest extends AbstractLexerTest {

    @Test
    public void testWhitespaceControlModifiers() {
        lexFile("src/test/resources/lexer/pebble/ws-ctrl-modifier.peb");
    }

    @Test
    public void testUnclosedVerbatim() {
        lexFile("src/test/resources/lexer/pebble/unclosed-verbatim.peb");
    }

    @Override
    PebbleLexer createLexer(String text) {
        return new PebbleLexer(new ANTLRInputStream(text));
    }
}
