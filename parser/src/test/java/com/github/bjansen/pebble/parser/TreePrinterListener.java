package com.github.bjansen.pebble.parser;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Trees;

import java.util.Arrays;
import java.util.List;

class TreePrinterListener implements ParseTreeListener {
    private final List<String> ruleNames;
    private final StringBuilder builder = new StringBuilder();

    public TreePrinterListener(Parser parser) {
        this.ruleNames = Arrays.asList(parser.getRuleNames());
    }

    public TreePrinterListener(List<String> ruleNames) {
        this.ruleNames = ruleNames;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (builder.length() > 0) {
            builder.append(' ');
        }

        builder.append(Utils.escapeWhitespace(Trees.getNodeText(node, ruleNames), false));
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        if (builder.length() > 0) {
            builder.append(' ');
        }

        builder.append(Utils.escapeWhitespace(Trees.getNodeText(node, ruleNames), false));
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if (builder.length() > 0) {
            builder.append(' ');
        }

        if (ctx.getChildCount() > 0) {
            builder.append('(');
        }

        int ruleIndex = ctx.getRuleIndex();
        String ruleName;
        if (ruleIndex >= 0 && ruleIndex < ruleNames.size()) {
            ruleName = ruleNames.get(ruleIndex);
        }
        else {
            ruleName = Integer.toString(ruleIndex);
        }

        builder.append(ruleName);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if (ctx.getChildCount() > 0) {
            builder.append(')');
        }
    }

    @Override
    public String toString() {
        return builder.toString().replaceAll("\\s*\\\\n\\s*", "\n");
    }
}