package com.intellij.spring.boot.application.metadata

import com.intellij.psi.PsiFile

/**
 * Dummy class to fix an exception when running tests.
 * [See this issue](https://youtrack.jetbrains.com/issue/IDEA-318652/Spring-tests-of-custom-plugin-depending-on-Spring-cannot-find-TestSpringBootMetadataEditorNotificationsService)
 */
@Suppress("unused")
class TestSpringBootMetadataEditorNotificationsService : SpringBootMetadataEditorNotificationsService {
    override fun updateData(psiFile: PsiFile) {
    }

    override fun isApplicable(): Boolean {
        return false
    }
}
