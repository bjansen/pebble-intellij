package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.lang.PebbleCore
import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.buildPsiTypeLookups
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.findMembersByName
import com.github.bjansen.intellij.pebble.psi.PebbleReferencesHelper.findQualifyingMember
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.PsiElementResolveResult.createResults
import com.intellij.psi.scope.util.PsiScopesUtil
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.PlatformIcons

class PebbleIdentifierReference(private val psi: PsiElement, private val range: TextRange) :
    PsiPolyVariantReferenceBase<PsiElement>(psi, range) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val qualifyingMember = findQualifyingMember(psi)
        val referenceText = range.substring(psi.text)

        if (qualifyingMember is PsiVariable) {
            val type = qualifyingMember.type
            if (type is PsiClassType) {
                return createResults(findMembersByName(type.resolve(), referenceText))
            }
        } else if (qualifyingMember is PsiMethod) {
            return createResults(findMembersByName(getPsiClassFromType(qualifyingMember.returnType), referenceText))
        } else {
            val parentTag = PsiTreeUtil.getParentOfType(psi, PebbleTagDirective::class.java)

            if (parentTag?.getTagName() == "filter" || psi.parent?.node?.elementType == rules[PebbleParser.RULE_filter]) {
                val source = PebbleCore.getFilter(psi.text, psi.project)?.source
                return if (source == null) createResults() else createResults(source)
            }

            if (parentTag != null && parentTag.getTagName() == "endblock") {
                return resolveOpeningBlockTag(parentTag, referenceText)
            }

            val file = psi.containingFile
            if (file is PebbleFile) {
                val variable = findVariableMatching(referenceText)

                if (variable != null) {
                    return createResults(variable)
                }

                for (func in file.getImplicitFunctions()) {
                    // TODO match signatures!
                    if (func.name == referenceText) {
                        return createResults(func)
                    }
                }
            }
        }

        return emptyArray()
    }

    private fun findVariableMatching(referenceText: String): PsiNamedElement? {
        var iv: PsiNamedElement? = null

        val processor = object : PebbleScopeProcessor {
            override fun execute(element: PsiElement, state: ResolveState): Boolean {
                if (element is PsiNamedElement && element.name == referenceText) {
                    iv = element
                    return false
                }
                return true
            }
        }
        PsiScopesUtil.treeWalkUp(processor, psi, psi.containingFile)

        return iv
    }

    private fun resolveOpeningBlockTag(closingBlockTag: PebbleTagDirective, blockName: String): Array<ResolveResult> {
        var child: PsiElement? = closingBlockTag.prevSibling

        while (child != null) {
            val parent = child.parent
            if (parent is PebbleBlockTag && parent.name == blockName) {
                return createResults(parent)
            }
            child = child.prevSibling
        }

        return emptyArray()
    }

    override fun getVariants(): Array<Any> {
        val qualifyingMember = findQualifyingMember(psi)

        if (qualifyingMember is PebbleInVariable) {
            return buildPsiTypeLookups(qualifyingMember.getType())
        } else if (qualifyingMember is PsiField) {
            return buildPsiTypeLookups(qualifyingMember.type)
        } else if (qualifyingMember is PsiVariable) {
            val file = element.containingFile

            if (file is PebbleFile) {
                var type: PsiType? = null

                val processor = object : PebbleScopeProcessor {
                    override fun execute(element: PsiElement, state: ResolveState): Boolean {
                        if (element is PsiVariable && element.name == qualifyingMember.text) {
                            type = element.type
                            return false
                        }
                        return true
                    }
                }
                PsiScopesUtil.treeWalkUp(processor, psi, psi.containingFile)

                if (type != null) {
                    return buildPsiTypeLookups(type)
                }
            }
        } else if (qualifyingMember is PsiMethod) {
            return buildPsiTypeLookups(qualifyingMember.returnType)
        } else if (qualifyingMember == null) {
            val file = psi.containingFile
            if (file is PebbleFile) {
                val result = mutableSetOf<LookupElement>()
                val processor = object : PebbleScopeProcessor {
                    override fun execute(element: PsiElement, state: ResolveState): Boolean {
                        if (element is PsiVariable) {
                            result.add(
                                LookupElementBuilder.create(element)
                                    .withTypeText(element.type.presentableText)
                                    .withIcon(PlatformIcons.VARIABLE_ICON)
                            )
                        } else if (element is PebbleInVariable) {
                            result.add(
                                LookupElementBuilder.create(element)
                                    .withTypeText(element.getType()?.presentableText)
                                    .withIcon(PlatformIcons.VARIABLE_ICON)
                            )
                        } else if (element is PebbleSetTag) {
                            result.add(
                                LookupElementBuilder.create(element)
                                    .withIcon(PlatformIcons.VARIABLE_ICON)
                            )
                        }
                        return true
                    }
                }
                PsiScopesUtil.treeWalkUp(processor, psi, psi.containingFile)

                result.addAll(
                    file.getImplicitFunctions().mapNotNull {
                        when (it) {
                            is PsiMethod -> {
                                val handler = ParenthesesInsertHandler.getInstance(it.parameterList.parametersCount > 0)
                                JavaLookupElementBuilder.forMethod(it, PsiSubstitutor.EMPTY)
                                    .withInsertHandler(handler)
                            }
                            is PebbleMacroTag -> {
                                val name = it.name
                                val params = it.getParameterNames()
                                val handler = ParenthesesInsertHandler.getInstance(params.isNotEmpty())

                                if (name == null)
                                    null
                                else {
                                    LookupElementBuilder.create(it, name)
                                        .withPresentableText(name)
                                        .withTailText(params.joinToString(", ", "(", ")"))
                                        .withTypeText("void")
                                        .withIcon(PlatformIcons.FUNCTION_ICON)
                                        .withInsertHandler(handler)
                                }
                            }
                            else -> null
                        }
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
