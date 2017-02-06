package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;

import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

abstract class AbstractLexerTest {

    abstract PebbleLexer createLexer(String text);

    void lexFile(String filePath) {
        try {
            String input = new String(readAllBytes(get(filePath)));
            PebbleLexer lexer = createLexer(input);
            PebbleParser parser = new PebbleParser(new CommonTokenStream(lexer));

            PebbleParser.PebbleTemplateContext tpl = parser.pebbleTemplate();
            TreePrinterListener printerListener = new TreePrinterListener(parser);
            ParseTreeWalker.DEFAULT.walk(printerListener, tpl);
            String actualOutput = printerListener.toString();

            String expectedOutputFile = filePath.replace(".peb", "-parsed.txt");
            String expectedOutput = new String(readAllBytes(get(expectedOutputFile)));

            Token previousToken = null;
            for (int i = 0; i < parser.getTokenStream().size(); i++) {
                Token token = parser.getTokenStream().get(i);
                if (previousToken != null && token.getType() != PebbleLexer.EOF) {
                    Assert.assertEquals("Tokens should be consecutive",
                            previousToken.getStopIndex() + 1, token.getStartIndex());
                }
                previousToken = token;
            }
            Assert.assertEquals(expectedOutput, actualOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
