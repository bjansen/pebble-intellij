package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.github.bjansen.intellij.pebble.parser.PebbleLexer
import com.github.bjansen.intellij.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType
import org.antlr.jetbrains.adaptor.parser.ANTLRParserAdaptor
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.Lexer as AntlrLexer
import org.antlr.v4.runtime.tree.ParseTree

fun createLexer(input: CharStream?) : Lexer {
    val antlrLexer = if (false) ConfigurableLexer(input) else PebbleLexer(input)
    return ANTLRLexerAdaptor(PebbleLanguage.INSTANCE, antlrLexer)
}

class PebbleParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer {
        return createLexer(null)
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    override fun getCommentTokens(): TokenSet {
        return COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project): ANTLRParserAdaptor {
        return object: ANTLRParserAdaptor(PebbleLanguage.INSTANCE, PebbleParser(null)){
            override fun parse(parser: Parser?, root: IElementType?): ParseTree {

                if (root is IFileElementType) {
                    return (parser as PebbleParser).pebbleTemplate()
                }

                throw UnsupportedOperationException("Can't parse ${root?.javaClass?.name}")
            }
        }
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return PebbleFile(viewProvider)
    }

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    override fun createElement(node: ASTNode): PsiElement {
        val elType = node.elementType

        if (elType == rules[PebbleParser.RULE_pebbleTemplate]) {
            return PebbleTemplate(node)
        } else if (elType == rules[PebbleParser.RULE_genericTag]
                || elType == rules[PebbleParser.RULE_verbatimTag]) {
            return PebbleTagDirective(node)
        } else if (elType == rules[PebbleParser.RULE_printDirective]) {
            return PebblePrintDirective(node)
        } else if (elType is TokenIElementType) {
            return ANTLRPsiNode(node)
        }

        return ANTLRPsiNode(node) // TODO
    }

    companion object {
        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(PebbleLanguage.INSTANCE,
                    PebbleLexer.tokenNames, PebbleParser.ruleNames)
        }

        val tokens: List<TokenIElementType> = PSIElementTypeFactory.getTokenIElementTypes(PebbleLanguage.INSTANCE)
        val rules: List<RuleIElementType> = PSIElementTypeFactory.getRuleIElementTypes(PebbleLanguage.INSTANCE)

        val WHITE_SPACES: TokenSet = PSIElementTypeFactory.createTokenSet(PebbleLanguage.INSTANCE, PebbleLexer.WHITESPACE)
        val COMMENTS: TokenSet = PSIElementTypeFactory.createTokenSet(PebbleLanguage.INSTANCE, PebbleLexer.COMMENT)

        val FILE = IFileElementType(Language.findInstance(PebbleLanguage::class.java))
    }
}
