package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil

object psiElementFactory {

    fun createIdentifier(name: String, project: Project): PebbleIdentifier {
        val file = createFile("{{$name}}", project)
        return PsiTreeUtil.findChildOfType(file, PebbleIdentifier::class.java)!!
    }

    fun createComment(text: String, project: Project): PebbleComment {
        val file = createFile(text, project)
        return file.firstChild as PebbleComment
    }

    fun createFile(content: String, project: Project): PsiFile {
        return PsiFileFactory.getInstance(project)
                .createFileFromText("a.peb", PebbleLanguage.INSTANCE, content)
    }
}
