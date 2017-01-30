package com.github.bjansen.intellij.pebble.psi;

import com.github.bjansen.intellij.pebble.parser.PebbleLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import java.util.Objects;

/**
 * A customizable {@link PebbleLexer} that allows overriding open and close delimiters
 * for tags and print directives.
 */
public class ConfigurableLexer extends PebbleLexer {

    private String printOpenDelimiter;
    private String printCloseDelimiter;
    private String tagOpenDelimiter;
    private String tagCloseDelimiter;

    public ConfigurableLexer(CharStream input) {
        super(input);
    }

    public ConfigurableLexer withPrintOpenDelimiter(String delimiter) {
        this.printOpenDelimiter = delimiter;
        return this;
    }

    public ConfigurableLexer withPrintCloseDelimiter(String delimiter) {
        this.printCloseDelimiter = delimiter;
        return this;
    }

    public ConfigurableLexer withTagOpenDelimiter(String delimiter) {
        this.tagOpenDelimiter = delimiter;
        return this;
    }

    public ConfigurableLexer withTagCloseDelimiter(String delimiter) {
        this.tagCloseDelimiter = delimiter;
        return this;
    }

    @Override
    public Token nextToken() {
        Token token;

        if (printOpenDelimiter != null
                && (token = tryMatching(printOpenDelimiter, PRINT_OPEN, IN_TAG)) != null) {
            return token;
        }
        if (printCloseDelimiter != null
                && (token = tryMatching(printCloseDelimiter, PRINT_CLOSE, null)) != null) {
            return token;
        }
        if (tagOpenDelimiter != null
            && (token = tryMatching(tagOpenDelimiter, TAG_OPEN, IN_TAG)) != null) {
            return token;
        }
        if (tagCloseDelimiter != null
            && (token = tryMatching(tagCloseDelimiter, TAG_CLOSE, null)) != null) {
            return token;
        }

        return super.nextToken();
    }

    private Token tryMatching(String delimiter, int type, Integer mode) {
        if (getCharIndex() >= 0 && getState() == -1) {
            String nextChars = _input.getText(Interval.of(getCharIndex(), getCharIndex() + delimiter.length() - 1));

            if (Objects.equals(nextChars, delimiter)) {
                this._token = null;
                this._channel = 0;
                this._tokenStartCharIndex = this._input.index();
                this._tokenStartCharPositionInLine = getInterpreter().getCharPositionInLine();
                this._tokenStartLine = getInterpreter().getLine();
                this._text = null;
                this._type = type;

                for (int i = 0; i < delimiter.length(); i++) {
                    this._input.consume();
                }

                if (mode != null) {
                    pushMode(mode);
                } else {
                    popMode();
                }
                return emit();
            }
        }

        return null;
    }
}
