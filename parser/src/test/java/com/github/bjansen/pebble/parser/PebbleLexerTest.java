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

    @Test
    public void testIssue31() {
        lexFile("src/test/resources/lexer/pebble/issue31-strings.peb");
    }

    @Test
    public void testIssue36() {
        lexFile("src/test/resources/lexer/pebble/issue36.peb");
    }

    @Test
    public void testIssue52() {
        lexFile("src/test/resources/lexer/pebble/issue52.peb");
    }

    @Test
    public void testIssue55() {
        lexFile("src/test/resources/lexer/pebble/issue55-filters.peb");
    }

    @Test
    public void testIssue65() {
        lexFile("src/test/resources/lexer/pebble/issue65-filters.peb");
    }

    @Test
    public void testSingleSharpString() {
        lexFile("src/test/resources/lexer/pebble/single-sharp-string.peb");
    }

    @Test
    public void testEscapedQuotes() {
        lexFile("src/test/resources/lexer/pebble/issue59.peb");
    }

    @Override
    PebbleLexer createLexer(String text) {
        return new PebbleLexer(new ANTLRInputStream(text));
    }
}
