package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.Trees;
import org.junit.Assert;

import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

abstract class AbstractLexerTest {

    abstract PebbleLexer createLexer(String text);

    protected void lexFile(String filePath) {
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

            Assert.assertEquals(expectedOutput, actualOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
