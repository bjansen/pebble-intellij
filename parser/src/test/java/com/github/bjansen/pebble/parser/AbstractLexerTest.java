package com.github.bjansen.pebble.parser;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;

abstract class AbstractLexerTest {

    abstract PebbleLexer createLexer(String text);

    void lexFile(String filePath) {
        try {
            String input = new String(readAllBytes(get(filePath)));
            PebbleLexer lexer = createLexer(input);
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

class SyntaxErrorListener extends BaseErrorListener {

    private List<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
        Object offendingSymbol, int line, int charPositionInLine, String msg,
        RecognitionException e) {

        errors.add(String.format("%s:%s %s", line, charPositionInLine, msg));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
