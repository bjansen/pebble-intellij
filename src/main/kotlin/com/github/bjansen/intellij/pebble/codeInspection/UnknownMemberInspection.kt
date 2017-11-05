package com.github.bjansen.intellij.pebble.codeInspection

import com.github.bjansen.intellij.pebble.PebbleBundle.message
import com.github.bjansen.intellij.pebble.psi.PebbleIdentifier
import com.github.bjansen.intellij.pebble.psi.PebbleIdentifierReference
import com.github.bjansen.intellij.pebble.psi.PebbleTagDirective
import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil.getParentOfType
import com.intellij.psi.util.PsiTreeUtil.nextVisibleLeaf

class UnknownMemberInspection : LocalInspectionTool() {

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

                        if (qualifier != null && ref.resolve() == null) {
                            val typeName = findTypeName(qualifier)

                            if (typeName != null) {
                                if (typeName == "void") {
                                    holder.registerProblem(
                                            element,
                                            message("inspection.unknown.attribute.null.message")
                                    )
                                } else {
                                    holder.registerProblem(
                                            element,
                                            message("inspection.unknown.attribute.message", element.name!!, typeName)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun findTypeName(element: PsiElement): String? {
        return when (element) {
            is PsiField -> element.type.canonicalText
            is PsiVariable -> element.type.canonicalText
            is PsiMethod -> element.returnType?.canonicalText ?: "void"
            else -> null
        }
    }
}
