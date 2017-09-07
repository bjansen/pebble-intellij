package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper.buildPsiTypeLookups
import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper.findMemberByName
import com.github.bjansen.intellij.pebble.psi.pebbleReferencesHelper.findQualifyingMember
import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.util.PlatformIcons

class PebbleIdentifierReference(private val psi: PsiElement, private val range: TextRange)
    : PsiReferenceBase<PsiElement>(psi, range) {

    override fun resolve(): PsiElement? {
        val qualifyingMember = findQualifyingMember(psi)
        val referenceText = range.substring(psi.text)

        if (qualifyingMember is PsiVariable) {
            val type = qualifyingMember.type
            if (type is PsiClassType) {
                return findMemberByName(type.resolve(), referenceText)
            }
        } else if (qualifyingMember is PsiMethod) {
            return findMemberByName(getPsiClassFromType(qualifyingMember.returnType), referenceText)
        } else {
            val file = psi.containingFile
            if (file is PebbleFile) {
                for (iv in file.getImplicitVariables()) {
                    if (iv.name == referenceText) {
                        return if (iv is PebbleImplicitVariable) iv.declaration ?: iv else iv
                    }
                }
                for (func in file.getImplicitFunctions()) {
                    // TODO match signatures!
                    if (func.name == referenceText) {
                        return func
                    }
                }
            }
        }

        return null
    }

    override fun getVariants(): Array<Any> {
        val qualifyingMember = findQualifyingMember(psi)

        if (qualifyingMember is PsiField) {
            return buildPsiTypeLookups(qualifyingMember.type)
        } else if (qualifyingMember is PsiVariable) {
            val file = element.containingFile

            if (file is PebbleFile) {
                val implicitVar = file.getImplicitVariables().find { it.name == qualifyingMember.text }

                if (implicitVar != null) {
                    return buildPsiTypeLookups(implicitVar.type)
                }
            }
        } else if (qualifyingMember is PsiMethod) {
            return buildPsiTypeLookups(qualifyingMember.returnType)
        } else if (qualifyingMember == null) {
            val file = psi.containingFile
            if (file is PebbleFile) {
                val result = arrayListOf<LookupElement>()
                result.addAll(
                        file.getImplicitVariables().map {
                            LookupElementBuilder.create(it)
                                    .withTypeText(it.type.presentableText)
                                    .withIcon(PlatformIcons.VARIABLE_ICON)
                        }
                )
                result.addAll(
                        file.getImplicitFunctions().map {
                            val handler = ParenthesesInsertHandler.getInstance(it.parameterList.parametersCount > 0)
                            JavaLookupElementBuilder.forMethod(it, PsiSubstitutor.EMPTY)
                                    .withInsertHandler(handler)
                        }
                )
                return result.toTypedArray()
            }
        }

        return emptyArray()
    }

    private fun getPsiClassFromType(type: PsiType?): PsiClass? {
        if (type is PsiClassType) {
            return type.resolve()
        }
        return null
    }
}
