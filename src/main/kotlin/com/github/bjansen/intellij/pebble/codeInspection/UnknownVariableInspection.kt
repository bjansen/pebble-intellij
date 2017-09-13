package com.github.bjansen.intellij.pebble.codeInspection

import com.github.bjansen.intellij.pebble.PebbleBundle
import com.github.bjansen.intellij.pebble.PebbleBundle.message
import com.github.bjansen.intellij.pebble.psi.*
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFixAndIntentionActionOnPsiElement
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil.getParentOfType
import com.intellij.psi.util.PsiTreeUtil.nextVisibleLeaf

class UnknownVariableInspection : LocalInspectionTool() {

    private val tagNamesToIgnore = arrayOf("block", "macro",
            "filter" /* TODO remove me when implementing #17*/)
    private val tagNamesDeclaringId = arrayOf("set", "for")

    override fun getDisplayName() = message("inspection.unknown.variable")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement?) {
                super.visitElement(element)

                if (element is PebbleIdentifier && element.textRange.length > 0) {
                    val parentTag = getParentOfType(element, PebbleTagDirective::class.java)
                    if (parentTag != null) {
                        if (parentTag.getTagName() in tagNamesToIgnore) {
                            return
                        }
                        if (parentTag.getTagName() in tagNamesDeclaringId
                                && element.textOffset == nextVisibleLeaf(parentTag.getTagNameElement()!!)?.textOffset) {
                            return
                        }
                    }

                    val ref = element.reference
                    if (ref is PebbleIdentifierReference) {
                        val qualifier = pebbleReferencesHelper.findQualifyingMember(element)

                        if (qualifier == null && ref.resolve() == null) {
                            holder.registerProblem(
                                    element,
                                    message("inspection.unknown.variable.message", element.name!!),
                                    AddImplicitVariableQuickFix(element)
                            )
                        }
                    }
                }
            }
        }
    }
}

class AddImplicitVariableQuickFix(element: PsiElement) : LocalQuickFixAndIntentionActionOnPsiElement(element) {

    override fun getFamilyName(): String {
        return "Pebble"
    }

    override fun getText(): String {
        return PebbleBundle.message("inspection.unknown.variable.quickfix")
    }

    override fun invoke(project: Project, file: PsiFile, editor: Editor?, startElement: PsiElement, endElement: PsiElement) {
        val tpl = TemplateSettings.getInstance().getTemplate("var", "Pebble")

        if (tpl is TemplateImpl && editor != null) {
            val directive = getParentOfType(startElement, PebbleTagDirective::class.java, PebblePrintDirective::class.java)
            if (directive != null) {
                editor.caretModel.moveToOffset(directive.startOffsetInParent)

                val params = mapOf("NAME" to startElement.text)
                TemplateManager.getInstance(project).startTemplate(editor, tpl, false, params, null)
            }
        }
    }
}
