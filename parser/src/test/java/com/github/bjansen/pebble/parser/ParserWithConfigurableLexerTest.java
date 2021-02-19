package com.github.bjansen.pebble.parser;

import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;

public class ParserWithConfigurableLexerTest extends AbstractParserTest {
    private boolean overridePrintDelimiters = true;

    @Test
    public void testSimpleTag() {
        parseFile("/lexer/configurable/simpletag.peb");
    }

    @Test
    public void testCustomizedTagAndDefaultTag() {
        parseFile("/lexer/configurable/customized-default.peb");
    }

    @Test
    public void testWhitespaceControlModifiers() {
        parseFile("/lexer/configurable/ws-ctrl-modifier.peb");
    }

    @Test
    public void testContentOnly() {
        parseFile("/lexer/configurable/content-only.peb");
    }

    @Test
    public void testMixed() {
        overridePrintDelimiters = false;
        parseFile("/lexer/configurable/mixed.peb");
        overridePrintDelimiters = true;
    }

    @Override
    PebbleLexer createLexer(InputStream text) throws IOException {
        ConfigurableLexer lexer = new ConfigurableLexer(CharStreams.fromStream(text))
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
