package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.PebbleParser
import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.github.bjansen.intellij.pebble.lang.PebbleLexerAdapter
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class PebbleParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer {
        return PebbleLexerAdapter()
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

    override fun createParser(project: Project): PebbleParser {
        return PebbleParser()
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
        return PebbleTypes.Factory.createElement(node)
    }

    companion object {

        val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
        val COMMENTS = TokenSet.create(PebbleTypes.COMMENT)

        val FILE = IFileElementType(Language.findInstance(PebbleLanguage::class.java))
    }
}