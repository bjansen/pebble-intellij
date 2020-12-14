package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.codeStyle.PebbleCodeStyleSettings
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.github.bjansen.pebble.parser.ConfigurableLexer
import com.github.bjansen.pebble.parser.PebbleLexer
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiBuilder
import com.intellij.lexer.Lexer
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParseTreeToPSIConverter
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import java.util.*

fun getPebbleCodeStyleSettings(project: Project?): PebbleCodeStyleSettings {
    if (ApplicationManager.getApplication().isUnitTestMode) {
        return PebbleCodeStyleSettings(CodeStyleSettings(false))
    }
    val codeStyleManager =
            if (project != null) CodeStyleSettingsManager.getInstance(project)
            else CodeStyleSettingsManager.getInstance()
    return codeStyleManager.currentSettings.getCustomSettings(PebbleCodeStyleSettings::class.java)
}

fun createLexer(input: CharStream?, project: Project?): Lexer {
    val ourSettings = getPebbleCodeStyleSettings(project)
    val antlrLexer =
            if (ourSettings.useDefaultDelimiters()) PebbleLexer(input)
            else ConfigurableLexer(input)
                    .withTagOpenDelimiter(ourSettings.tagOpen)
                    .withTagCloseDelimiter(ourSettings.tagClose)
                    .withPrintOpenDelimiter(ourSettings.printOpen)
                    .withPrintCloseDelimiter(ourSettings.printClose)

    return ANTLRLexerAdaptor(PebbleLanguage.INSTANCE, antlrLexer)
}

class PebbleParserDefinition : ParserDefinition {

    private val knowOpeningTagsElementTypes = arrayOf(
            autoescapeElementType, blockElementType, cacheElementType, filterElementType, forElementType,
            ifElementType, macroElementType, parallelElementType
    )
    private val knownOpeningTags = knowOpeningTagsElementTypes.map { r -> r.toString() }
    private val knownClosingTags = knownOpeningTags.map { "end$it" }

    override fun createLexer(project: Project): Lexer {
        return createLexer(null, project)
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
        return object : ANTLRParserAdaptor(PebbleLanguage.INSTANCE, PebbleParser(null)) {
            override fun parse(parser: Parser?, root: IElementType?): ParseTree {

                if (root is IFileElementType) {
                    return (parser as PebbleParser).pebbleTemplate()
                }

                throw UnsupportedOperationException("Can't parse ${root?.javaClass?.name}")
            }

            override fun createListener(parser: Parser?, root: IElementType?, builder: PsiBuilder?): ANTLRParseTreeToPSIConverter {
                return object : ANTLRParseTreeToPSIConverter(PebbleLanguage.INSTANCE, parser, builder) {

                    val markedTags = Stack<Pair<PsiBuilder.Marker, Int>>()

                    override fun exitEveryRule(ctx: ParserRuleContext) {
                        if (ctx.ruleIndex == PebbleParser.RULE_tagDirective) {
                            val firstChild = ctx.children?.get(0)
                            if (firstChild is PebbleParser.GenericTagContext) {
                                val tagName = firstChild.tagName().text

                                if (tagName in knownOpeningTags) {
                                    markedTags.push(Pair(markers.pop(), specialElementTypeStart + knownOpeningTags.indexOf(tagName)))
                                    return
                                }
                                if (tagName in knownClosingTags
                                        && markedTags.isNotEmpty()) {
                                    super.exitEveryRule(ctx)

                                    // call done on the opening tag
                                    val marked = markedTags.pop()
                                    marked.first.done(getRuleElementType(marked.second))

                                    return
                                }
                            }
                        } else if (ctx.ruleIndex == PebbleParser.RULE_pebbleTemplate) {
                            // call done on unclosed tags
                            while (markedTags.isNotEmpty()) {
                                val marked = markedTags.pop()
                                marked.first.done(getRuleElementType(marked.second))
                            }
                        }

                        super.exitEveryRule(ctx)
                    }

                    private fun getRuleElementType(index: Int): IElementType {
                        return if (index >= specialElementTypeStart) {
                            knowOpeningTagsElementTypes[index - specialElementTypeStart]
                        } else {
                            getRuleElementTypes()[index]
                        }
                    }
                }
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
        return PsiElementFactory.createElement(node)
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
        val TAG_NAMES: TokenSet = PSIElementTypeFactory.createTokenSet(PebbleLanguage.INSTANCE,
            PebbleLexer.ID_NAME, PebbleLexer.IMPORT, PebbleLexer.FROM)

        // "Soft" keywords can also be valid identifiers. We only highlight them when their parent is not a RULE_identifier
        val SOFT_KEYWORDS: TokenSet = PSIElementTypeFactory.createTokenSet(PebbleLanguage.INSTANCE,
            PebbleLexer.AS, PebbleLexer.FROM, PebbleLexer.IN, PebbleLexer.IMPORT, PebbleLexer.WITH)

        val FILE = IFileElementType(Language.findInstance(PebbleLanguage::class.java))

        private const val specialElementTypeStart = 200

        val autoescapeElementType = RuleIElementType(specialElementTypeStart, "autoescape", PebbleLanguage.INSTANCE)
        val blockElementType = RuleIElementType(specialElementTypeStart + 1, "block", PebbleLanguage.INSTANCE)
        val cacheElementType = RuleIElementType(specialElementTypeStart + 2, "cache", PebbleLanguage.INSTANCE)
        val filterElementType = RuleIElementType(specialElementTypeStart + 3, "filter", PebbleLanguage.INSTANCE)
        val forElementType = RuleIElementType(specialElementTypeStart + 4, "for", PebbleLanguage.INSTANCE)
        val ifElementType = RuleIElementType(specialElementTypeStart + 5, "if", PebbleLanguage.INSTANCE)
        val macroElementType = RuleIElementType(specialElementTypeStart + 6, "macro", PebbleLanguage.INSTANCE)
        val parallelElementType = RuleIElementType(specialElementTypeStart + 7, "parallel", PebbleLanguage.INSTANCE)

        fun isTagDirectiveLike(elType: IElementType): Boolean {
            return elType == rules[PebbleParser.RULE_tagDirective]
                    || elType == rules[PebbleParser.RULE_verbatimTag]
                    || (elType is RuleIElementType && elType.ruleIndex >= specialElementTypeStart)
        }
    }
}
