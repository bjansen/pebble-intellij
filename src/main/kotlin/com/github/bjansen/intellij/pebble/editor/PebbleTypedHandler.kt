package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.lang.PebbleFileViewProvider
import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil.insertStringAtCaret
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import java.util.regex.Pattern

/**
 * Automatically close delimiters.
 */
// TODO This doesn't support custom delimiters
class PebbleTypedHandler : TypedHandlerDelegate() {

    val tagNamePattern: Pattern = Pattern.compile("\\{%\\s+(\\w+).*")
    val tagsThatCanBeClosed = mapOf(
            "autoescape" to "endautoescape",
            "block" to "endblock",
            "cache" to "endcache",
            "filter" to "endfilter",
            "for" to "endfor",
            "if" to "endif",
            "macro" to "endmacro",
            "parallel" to "endparallel"
    )

    override fun beforeCharTyped(c: Char, project: Project?, editor: Editor, file: PsiFile, fileType: FileType?): Result {
        if (file.viewProvider !is PebbleFileViewProvider) {
            return TypedHandlerDelegate.Result.CONTINUE
        }
        if (c != '}' && c != '#' && c != '{') {
            return TypedHandlerDelegate.Result.CONTINUE
        }

        val offset = editor.caretModel.offset
        val charBefore = getCharAt(editor.document, offset - 1)
        val charAfter = getCharAt(editor.document, offset)
        val buf = StringBuilder()
        var caretShift = 2

        if (charBefore == '{' && (c == '#' || c == '{')) {
            // autoclose the comment
            buf.append(c).append(" ")
            if (needsClosingDelimiter(editor, offset, c)) {
                buf.append(" ").append(if (c == '#') c else '}')
                if (charAfter != '}') {
                    buf.append('}')
                }
            }
        }

        if (charBefore == '%' && c == '}') {
            val closingTag = tagsThatCanBeClosed[findOpeningTagName(editor)]

            if (closingTag != null) {
                buf.append("}{% ").append(closingTag).append(" %}")
            }
            caretShift = 1
        }

        if (buf.isEmpty()) {
            return TypedHandlerDelegate.Result.CONTINUE
        }

        insertStringAtCaret(editor, buf.toString(), true, caretShift)

        val documentManager = PsiDocumentManager.getInstance(project as Project)
        documentManager.commitDocument(editor.document)
        return TypedHandlerDelegate.Result.STOP
    }

    private fun findOpeningTagName(editor: Editor): String? {
        var offset = editor.caretModel.offset

        while (offset > 0) {
            if (getCharAt(editor.document, offset) == '%' && getCharAt(editor.document, offset - 1) == '{') {
                val tag = editor.document.text.substring(offset-1..editor.caretModel.offset - 1)
                val matcher = tagNamePattern.matcher(tag)

                if (matcher.matches()) {
                    return matcher.group(1)
                }
            }
            offset--
        }

        return null
    }

    private fun needsClosingDelimiter(editor: Editor, afterOffset: Int, openingChar: Char): Boolean {
        val closingChar = if (openingChar == '{') '}' else openingChar
        val docCharSequence = editor.document.charsSequence

        for (offset in afterOffset..docCharSequence.length - 1) {
            val nextChar = if (offset + 1 < docCharSequence.length)
                docCharSequence[offset + 1]
                else '\u0000'
            val currChar = docCharSequence[offset]

            if (currChar == '{' && nextChar == openingChar) {
                return true
            }

            if (currChar != closingChar || nextChar != '}') {
                continue
            }
            return false
        }

        return true
    }

    private fun getCharAt(document: Document, offset: Int): Char {
        if (offset >= document.textLength || offset < 0) {
            return '\u0000'
        }
        return document.charsSequence[offset]
    }

}

class PebbleEnterBetweenTagsHandler : EnterHandlerDelegateAdapter() {
    override fun preprocessEnter(file: PsiFile, editor: Editor, caretOffset: Ref<Int>, caretAdvance: Ref<Int>,
                                 dataContext: DataContext, originalHandler: EditorActionHandler?): EnterHandlerDelegate.Result {

        if (file is PebbleFile && betweenMatchingTags(editor, caretOffset.get())) {
            editor.document.insertString(caretOffset.get(), "\n")
        }

        return EnterHandlerDelegate.Result.Continue
    }

    fun betweenMatchingTags(editor: Editor, offset: Int): Boolean {
        if (offset < 2) {
            return false
        }
        val chars = editor.document.charsSequence
        if (chars[offset - 1] != '}' || chars[offset - 2] != '%') {
            return false
        }

        val highlighter = (editor as EditorEx).highlighter
        val iterator = highlighter.createIterator(offset - 1)
        if (iterator.tokenType !== tokens[PebbleLexer.TAG_CLOSE]) {
            return false
        }

        iterator.advance()

        if (!iterator.atEnd() && iterator.tokenType === tokens[PebbleLexer.TAG_OPEN]) {
            // see if it's an "endXXX" tag
            do {
                iterator.advance()
            } while (!iterator.atEnd() && iterator.tokenType === TokenType.WHITE_SPACE)

            return !iterator.atEnd()
                    && iterator.tokenType === tokens[PebbleLexer.ID_NAME]
                    && chars.substring(iterator.start..iterator.end).startsWith("end")
        }

        return false
    }
}
