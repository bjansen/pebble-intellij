package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Test;

public class ConfigurableLexerTest extends AbstractLexerTest {
    private boolean overridePrintDelimiters = true;

    @Test
    public void testSimpleTag() {
        lexFile("src/test/resources/lexer/configurable/simpletag.peb");
    }

    @Test
    public void testCustomizedTagAndDefaultTag() {
        lexFile("src/test/resources/lexer/configurable/customized-default.peb");
    }

    @Test
    public void testWhitespaceControlModifiers() {
        lexFile("src/test/resources/lexer/configurable/ws-ctrl-modifier.peb");
    }

    @Test
    public void testContentOnly() {
        lexFile("src/test/resources/lexer/configurable/content-only.peb");
    }

    @Test
    public void testMixed() {
        overridePrintDelimiters = false;
        lexFile("src/test/resources/lexer/configurable/mixed.peb");
        overridePrintDelimiters = true;
    }

    @Override
    PebbleLexer createLexer(String text) {
        ConfigurableLexer lexer = new ConfigurableLexer(new ANTLRInputStream(text))
                .withTagOpenDelimiter("{*")
                .withTagCloseDelimiter("*}");

        if (overridePrintDelimiters) {
            lexer = lexer
                    .withPrintOpenDelimiter("<<")
                    .withPrintCloseDelimiter(">>");
        }
        return lexer;
    }
}
