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

    @Test
    public void testIssue14() {
        lexFile("src/test/resources/lexer/pebble/issue14.peb");
    }

    @Test
    public void testIssue22() {
        lexFile("src/test/resources/lexer/pebble/issue22.peb");
    }

    @Test
    public void testIssue24() {
        lexFile("src/test/resources/lexer/pebble/issue24.peb");
    }

    @Test
    public void testIssue27() {
        lexFile("src/test/resources/lexer/pebble/issue27-content-in-verbatim.peb");
    }

    @Override
    PebbleLexer createLexer(String text) {
        return new PebbleLexer(new ANTLRInputStream(text));
    }
}
