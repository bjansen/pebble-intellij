package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.lexer.TokenIElementType

object PsiElementFactory {

    fun createIdentifier(name: String, project: Project): PebbleIdentifier {
        val file = createFile("{{$name}}", project)
        return PsiTreeUtil.findChildOfType(file, PebbleIdentifier::class.java)!!
    }

    fun createComment(text: String, project: Project): PebbleComment {
        val file = createFile(text, project)
        return file.firstChild as PebbleComment
    }

    private fun createFile(content: String, project: Project): PsiFile {
        return PsiFileFactory.getInstance(project)
                .createFileFromText("a.peb", PebbleLanguage.INSTANCE, content)
    }

    fun createElement(node: ASTNode): PsiElement {
        val elType = node.elementType

        if (elType == rules[PebbleParser.RULE_pebbleTemplate]) {
            return PebbleTemplate(node)
        } else if (elType == PebbleParserDefinition.forElementType) {
            return PebbleForTag(node)
        } else if (elType == PebbleParserDefinition.macroElementType) {
            return PebbleMacroTag(node)
        } else if (elType == PebbleParserDefinition.blockElementType) {
            return PebbleBlockTag(node)
        } else if (PebbleParserDefinition.isTagDirectiveLike(elType) && node.firstChildNode != null) {
            return when (node.firstChildNode.findChildByType(rules[PebbleParser.RULE_tagName])?.text) {
                "set" -> PebbleSetTag(node)
                else -> PebbleTagDirective(node)
            }
        } else if (elType == rules[PebbleParser.RULE_printDirective]) {
            return PebblePrintDirective(node)
        } else if (elType == rules[PebbleParser.RULE_identifier]) {
            if (node.treeParent?.elementType == rules[PebbleParser.RULE_in_expression]) {
                return PebbleInVariable(node)
            }
            return PebbleIdentifier(node)
        } else if (elType == rules[PebbleParser.RULE_argument_list]) {
            return PebbleArgumentList(node)
        } else if (elType is TokenIElementType) {
            return PebblePsiElement(node)
        }

        return PebblePsiElement(node) // TODO
    }
}

