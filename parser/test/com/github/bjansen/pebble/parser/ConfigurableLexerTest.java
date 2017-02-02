package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Test;

public class ConfigurableLexerTest extends AbstractLexerTest {
    @Test
    public void testSimpleTag() {
        lexFile("test-resources/lexer/configurable/simpletag.peb");
    }

    @Test
    public void testCustomizedTagAndDefaultTag() {
        lexFile("test-resources/lexer/configurable/customized-default.peb");
    }

    @Override
    PebbleLexer createLexer(String text) {
        return new ConfigurableLexer(new ANTLRInputStream(text))
                .withTagOpenDelimiter("{*")
                .withTagCloseDelimiter("*}");
    }
}
