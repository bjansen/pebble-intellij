package com.github.bjansen.intellij.pebble.utils

import com.google.common.io.CharStreams
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile
import java.io.InputStreamReader

object ResourceUtil {

    fun loadPsiClassFromFile(fileName: String, key: Key<PsiClass>, project: Project): PsiClass? {
        val cached = project.getUserData(key)

        if (cached != null) {
            return cached
        }

        val resource = ResourceUtil::class.java
                .getResourceAsStream(fileName)

        if (resource != null) {
            val text = CharStreams.toString(InputStreamReader(resource, Charsets.UTF_8))
            val javaFile = PsiFileFactory.getInstance(project)
                    .createFileFromText("a.java", JavaFileType.INSTANCE, text) as PsiJavaFile
            val clazz = javaFile.classes[0]
            project.putUserData(key, clazz)
            return clazz
        }

        project.putUserData(key, null)

        return null
    }

}
