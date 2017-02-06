package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
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

    /**
     * Try matching customized delimiters instead of the default ones, which
     * will become {@link #CONTENT} instead.
     */
    @Override
    protected Token customNextToken() {
        // TODO we could do better and merge consecutive CONTENT tokens

        if (printOpenDelimiter != null) {
            if (tryMatching(printOpenDelimiter + "-", PRINT_OPEN, IN_TAG)
                    || tryMatching(printOpenDelimiter, PRINT_OPEN, IN_TAG)) {
                return emit();
            }
            if (tryMatching("{{", CONTENT, null)) {
                return nextContentToken("{{");
            }
        }
        if (printCloseDelimiter != null && _mode == IN_TAG) {
            if (tryMatching("-" + printCloseDelimiter, PRINT_CLOSE, -1)
                    || tryMatching(printCloseDelimiter, PRINT_CLOSE, -1)) {
                return emit();
            }
            if (tryMatching("}}", CONTENT, null)) {
                return nextContentToken("}}");
            }
        }
        if (tagOpenDelimiter != null) {
            if (tryMatching(tagOpenDelimiter + "-", TAG_OPEN, IN_TAG)
                    || tryMatching(tagOpenDelimiter, TAG_OPEN, IN_TAG)) {
                return emit();
            }
            if (tryMatching("{%", CONTENT, null)) {
                return nextContentToken("{%");
            }
        }
        if (tagCloseDelimiter != null && _mode == IN_TAG) {
            if (tryMatching("-" + tagCloseDelimiter, TAG_CLOSE, -1)
                    || tryMatching(tagCloseDelimiter, TAG_CLOSE, -1)) {
                return emit();
            }
            if (tryMatching("%}", CONTENT, null)) {
                return nextContentToken("%}");
            }
        }

        return super.customNextToken();
    }

    private Token nextContentToken(String delimiter) {
        if (getCharIndex() == _input.size()) {
            // end of file
            return emit();
        }
        CommonToken token = (CommonToken) super.customNextToken();
        token.setStartIndex(token.getStartIndex() - delimiter.length());
        return token;
    }

    private boolean tryMatching(String delimiter, int type, Integer mode) {
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
                    if (mode == -1) {
                        if (_modeStack.size() > 0) {
                            popMode();
                        } else {
                            System.out.println("WARN: can't pop mode (empty stack)");
                        }
                    } else {
                        pushMode(mode);
                    }
                }
                return true;
            }
        }

        return false;
    }
}
