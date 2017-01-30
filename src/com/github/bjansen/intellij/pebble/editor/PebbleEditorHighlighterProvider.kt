package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.parser.PebbleLexer
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.editor.highlighter.EditorHighlighter
import com.intellij.openapi.fileTypes.EditorHighlighterProvider
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.StdFileTypes
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings

/**
 * Allows using Pebble templates inside other file types.
 */
class PebbleEditorHighlighterProvider : EditorHighlighterProvider {
    override fun getEditorHighlighter(project: Project?, fileType: FileType,
                                      virtualFile: VirtualFile?, colors: EditorColorsScheme): EditorHighlighter {
        return PebbleTemplateHighlighter(project, virtualFile, colors)
    }
}

internal class PebbleTemplateHighlighter(project: Project?, virtualFile: VirtualFile?, colors: EditorColorsScheme)
        : LayeredLexerEditorHighlighter(PebbleHighlighter(), colors) {

    init {
        // highlighter for outer lang
        var type: FileType? = null

        if (project == null || virtualFile == null) {
            type = StdFileTypes.PLAIN_TEXT
        } else {
            val language = TemplateDataLanguageMappings.getInstance(project).getMapping(virtualFile)
            if (language != null) {
                type = language.associatedFileType
            }
            if (type == null) {
                type = StdFileTypes.PLAIN_TEXT
            }
        }

        if (type != null) {
            val outerHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(type, project, virtualFile)
            registerLayer(tokens[PebbleLexer.CONTENT], LayerDescriptor(outerHighlighter, ""))
        }
    }
}