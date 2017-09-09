package com.github.bjansen.intellij.pebble.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider
import com.intellij.psi.impl.source.tree.PsiCoreCommentImpl
import com.intellij.psi.tree.IElementType

class PebbleComment(type: IElementType, text: CharSequence) : PsiCoreCommentImpl(type, text), PsiNamedElement {

    override fun setName(name: String): PsiElement {
        val nameRange = getValueRange(text, "name")
        if (nameRange != null) {
            val newText = nameRange.replace(text, name)
            return replace(psiElementFactory.createComment(newText, project))
        }
        return this
    }

    override fun getName(): String? {
        return extractValue(text, "name")
    }

    companion object {

        fun getImplicitVariable(comment: PebbleComment): ImplicitVariable? {
            if (comment.text.contains("@pebvariable")) {
                val name = extractValue(comment.text, "name")
                val type = extractValue(comment.text, "type")

                if (name != null && type != null && type.isNotEmpty()) {
                    val fragment = JavaCodeFragmentFactory.getInstance(comment.project)
                            .createTypeCodeFragment(type, null, false)
                    fragment.forceResolveScope(comment.resolveScope)

                    try {
                        return PebbleImplicitVariable(name, fragment.type, comment.containingFile, comment)
                    } catch (e: PsiTypeCodeFragment.IncorrectTypeException) {
                        e.printStackTrace()
                    }
                }
            }

            return null
        }

        private fun extractValue(text: String, key: String): String? {
            val range = getValueRange(text, key)

            return range?.substring(text)
        }

        private fun getValueRange(text: String, key: String): TextRange? {
            val prefix = "$key=\""
            val start = text.indexOf(prefix)

            if (start >= 0) {
                val stop = text.indexOf("\"", start + prefix.length)

                if (stop >= 0) {
                    return TextRange(start + prefix.length, stop)
                }
            }

            return null
        }
    }

    override fun getReferences(): Array<PsiReference> {
        val refs = arrayListOf<PsiReference>()

        if (text.contains("@pebvariable")) {
            val nameRange = getValueRange(text, "name")
            if (nameRange != null) {
                refs.add(PebbleCommentReference(this, nameRange))
            }

            val typeRange = getValueRange(text, "type")
            if (typeRange != null) {
                refs.addAll(JavaClassReferenceProvider().getReferencesByString(typeRange.substring(text), this, typeRange.startOffset))
            }
        }

        return refs.toTypedArray()
    }
}

class PebbleCommentReference(comment: PebbleComment, range: TextRange)
    : PsiReferenceBase.Immediate<PebbleComment>(comment, range, comment)
