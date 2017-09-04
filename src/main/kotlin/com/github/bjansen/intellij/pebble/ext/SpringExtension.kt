package com.github.bjansen.intellij.pebble.ext

import com.github.bjansen.intellij.pebble.psi.PebbleFile
import com.google.common.io.CharStreams
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import java.io.InputStreamReader

object springExtension {
    private val key = Key.create<PsiClass>("PEBBLE_SPRING_CLASS")

    private fun getPebbleSpringClass(project: Project): PsiClass? {
        val cached = project.getUserData(key)

        if (cached != null) {
            return cached
        }

        val resource = springExtension::class.java
                .getResourceAsStream("/implicitCode/PebbleSpring.java")

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

    fun getImplicitVariables(file: PebbleFile): List<PsiVariable> {
        if (isPebbleSpringAvailable(file) || ApplicationManager.getApplication().isUnitTestMode) {
            val clazz = getPebbleSpringClass(file.project)
            if (clazz != null) {
                return clazz.allFields.asList()
            }
        }

        return emptyList()
    }

    fun getImplicitFunctions(file: PebbleFile): List<PsiMethod> {
        if (isPebbleSpringAvailable(file) || ApplicationManager.getApplication().isUnitTestMode) {
            val clazz = getPebbleSpringClass(file.project)
            if (clazz != null) {
                return clazz.allMethods.asList()
            }
        }

        return emptyList()
    }

    private fun getSearchScope(file: PsiFile): GlobalSearchScope? {
        val module = ModuleUtil.findModuleForPsiElement(file)

        return module?.getModuleWithDependenciesAndLibrariesScope(false)
    }

    private fun isPebbleSpringAvailable(file: PsiFile): Boolean {
        val scope = getSearchScope(file)

        if (scope != null) {
            val spring3 = JavaPsiFacade.getInstance(file.project)
                    .findClass("com.mitchellbosecke.pebble.spring.PebbleView", scope)
            val spring4 = JavaPsiFacade.getInstance(file.project)
                    .findClass("com.mitchellbosecke.pebble.spring4.PebbleView", scope)

            return spring3 != null || spring4 != null
        }

        return false
    }
}
