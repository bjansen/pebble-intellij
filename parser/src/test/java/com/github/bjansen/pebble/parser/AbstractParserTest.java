package com.github.bjansen.pebble.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;

abstract class AbstractParserTest {

    abstract PebbleLexer createLexer(InputStream text) throws IOException;

    void parseFile(String filePath) {
        try (InputStream in = getClass().getResourceAsStream(filePath)) {
            PebbleLexer lexer = createLexer(in);
            PebbleParser parser = new PebbleParser(new CommonTokenStream(lexer));

            SyntaxErrorListener errorListener = new SyntaxErrorListener();
            parser.addErrorListener(errorListener);

            PebbleParser.PebbleTemplateContext tpl = parser.pebbleTemplate();
            TreePrinterListener printerListener = new TreePrinterListener(parser);
            ParseTreeWalker.DEFAULT.walk(printerListener, tpl);
            String actualOutput = printerListener.toString();

            if (errorListener.hasErrors()) {
                actualOutput += "\n\nSyntax errors:\n\n" + Utils.join(errorListener.getErrors().iterator(), "\n");
            }

            String expectedOutputFile = filePath.replace(".peb", "-parsed.txt");
            String expectedOutput = readString(getClass().getResourceAsStream(expectedOutputFile));

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

    private static String readString(InputStream is) {
        try (Scanner s = new Scanner(is).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }
}
