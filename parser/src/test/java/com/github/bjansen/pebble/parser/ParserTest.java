package com.github.bjansen.pebble.parser;

import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;

public class ParserTest extends AbstractParserTest {

    @Test
    public void testWhitespaceControlModifiers() {
        parseFile("/lexer/pebble/ws-ctrl-modifier.peb");
    }

    @Test
    public void testUnclosedVerbatim() {
        parseFile("/lexer/pebble/unclosed-verbatim.peb");
    }

    @Test
    public void testIssue14() {
        parseFile("/lexer/pebble/issue14.peb");
    }

    @Test
    public void testIssue22() {
        parseFile("/lexer/pebble/issue22.peb");
    }

    @Test
    public void testIssue24() {
        parseFile("/lexer/pebble/issue24.peb");
    }

    @Test
    public void testIssue27() {
        parseFile("/lexer/pebble/issue27-content-in-verbatim.peb");
    }

    @Test
    public void testIssue31() {
        parseFile("/lexer/pebble/issue31-strings.peb");
    }

    @Test
    public void testIssue36() {
        parseFile("/lexer/pebble/issue36.peb");
    }

    @Test
    public void testIssue52() {
        parseFile("/lexer/pebble/issue52.peb");
    }

    @Test
    public void testIssue55() {
        parseFile("/lexer/pebble/issue55-filters.peb");
    }

    @Test
    public void testIssue65() {
        parseFile("/lexer/pebble/issue65-filters.peb");
    }

    @Test
    public void testSingleSharpString() {
        parseFile("/lexer/pebble/single-sharp-string.peb");
    }

    @Test
    public void testEscapedQuotes() {
        parseFile("/lexer/pebble/issue59.peb");
    }

    @Test
    public void testStrings() {
        parseFile("/lexer/pebble/strings.peb");
    }

    @Override
    PebbleLexer createLexer(InputStream text) throws IOException {
        return new PebbleLexer(CharStreams.fromStream(text));
    }
}
