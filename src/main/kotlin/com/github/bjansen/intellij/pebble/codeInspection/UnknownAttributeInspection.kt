package com.github.bjansen.intellij.pebble.codeInspection

import com.github.bjansen.intellij.pebble.PebbleBundle.message
import com.github.bjansen.intellij.pebble.psi.*
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil.getParentOfType
import com.intellij.psi.util.PsiTreeUtil.nextVisibleLeaf

class UnknownAttributeInspection : LocalInspectionTool() {

    private val tagNamesToIgnore = arrayOf("block", "macro")
    private val tagNamesDeclaringId = arrayOf("set", "for")

    override fun getDisplayName() = message("inspection.unknown.variable")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
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

                    inspectIdentifier(element)
                }
            }

            private fun inspectIdentifier(element: PebbleIdentifier) {
                val ref = element.reference
                if (ref is PebbleIdentifierReference) {
                    val qualifier = PebbleReferencesHelper.findQualifyingMember(element)

                    if (qualifier != null && ref.multiResolve(false).isEmpty()) {
                        val type = when (qualifier) {
                            is PsiField -> qualifier.type
                            is PsiVariable -> qualifier.type
                            is PebbleInVariable -> qualifier.getType()
                            is PebbleLiteral -> qualifier.getType()
                            is PsiMethod -> qualifier.returnType ?: PsiTypes.voidType()
                            else -> null
                        } ?: return

                        when {
                            type == PsiTypes.voidType() -> holder.registerProblem(
                                    element,
                                    message("inspection.unknown.attribute.null.message")
                            )
                            type.canonicalText.startsWith("java.util.Map") -> {
                                // ignore it, we can't be sure if the element exists
                            }
                            else -> holder.registerProblem(
                                    element,
                                    message("inspection.unknown.attribute.message", element.name!!, type.canonicalText)
                            )
                        }
                    }
                }
            }
        }
    }
}
