package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.rules
import com.github.bjansen.pebble.parser.PebbleLexer
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.*
import com.intellij.psi.search.searches.SuperMethodsSearch
import com.intellij.psi.util.PropertyUtil
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.PlatformIcons

class PebbleIdentifierReference(private val psi: PsiElement, private val range: TextRange)
    : PsiReferenceBase<PsiElement>(psi, range) {

    override fun resolve(): PsiElement? {
        val qualifyingMember = findQualifyingMember()
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

    private fun findMemberByName(clazz: PsiClass?, name: String): PsiElement? {
        if (clazz != null) {
            val method = clazz.allMethods
                    .find { getPropertyNameAndType(it)?.first == name || it.name == name }

            val field = clazz.fields
                    .filter { it.hasModifierProperty(PsiModifier.PUBLIC) }
                    .find { it.name == name }

            return method ?: field
        }

        return null
    }

    override fun getVariants(): Array<Any> {
        val qualifyingMember = findQualifyingMember()

        if (qualifyingMember is PsiField) {
            return buildPsiTypeLookups(qualifyingMember.type)
        } else if (qualifyingMember is PsiVariable) {
            if (qualifyingMember.text == "beans") {
                TODO("need a dependency on the Spring plugin")
            } else {
                val file = element.containingFile

                if (file is PebbleFile) {
                    val implicitVar = file.getImplicitVariables().find { it.name == qualifyingMember.text }

                    if (implicitVar != null) {
                        return buildPsiTypeLookups(implicitVar.type)
                    }
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

    private fun isOverride(method: PsiMethod)
            = SuperMethodsSearch.search(method, null, true, false).findFirst() != null

    private fun buildPsiTypeLookups(type: PsiType?): Array<Any> {
        if (type is PsiClassType) {
            val clazz = type.resolve() ?: return emptyArray()
            val resolveResult = type.resolveGenerics()

            val lookups = arrayListOf<LookupElement>()

            lookups.addAll(clazz.allMethods
                    .filter {
                        it.hasModifierProperty(PsiModifier.PUBLIC)
                                && !isOverride(it)
                                && !it.isConstructor
                    }
                    .map {
                        val prop = getPropertyNameAndType(it)
                        if (prop != null) {
                            LookupElementBuilder.create(prop.first)
                                    .withIcon(AllIcons.Nodes.Property)
                                    .withTypeText(prop.second.presentableText)
                        } else {
                            val handler = ParenthesesInsertHandler.getInstance(it.parameterList.parametersCount > 0)
                            JavaLookupElementBuilder.forMethod(it, LambdaUtil.getSubstitutor(it, resolveResult))
                                    .withInsertHandler(handler)
                        }
                    }
            )

            lookups.addAll(clazz.allFields
                    .filter {
                        it.hasModifierProperty(PsiModifier.PUBLIC)
                    }
                    .map(JavaLookupElementBuilder::forField)
            )

            return lookups.toTypedArray()
        }

        return emptyArray()
    }

    private fun getPropertyNameAndType(method: PsiMethod): Pair<String, PsiType>? {
        val prop = PropertyUtil.getPropertyName(method)

        if (prop != null) {
            return Pair(prop, PropertyUtil.getPropertyType(method)!!)
        }

        val methodNameLength = method.name.length
        if (method.name.startsWith("has")
                && methodNameLength > "has".length
                && method.name[3].isUpperCase()
                && method.parameterList.parametersCount == 0) {

            return Pair(StringUtil.decapitalize(method.name.substring(3)), method.returnType!!)
        }

        return null
    }

    private fun findQualifyingMember(): PsiElement? {
        val prevLeaf = PsiTreeUtil.prevVisibleLeaf(psi)

        if (prevLeaf != null && prevLeaf.node.elementType == PebbleParserDefinition.tokens[PebbleLexer.OP_MEMBER]) {
            val qualifier = prevLeaf.prevSibling

            if (qualifier != null) {
                val identifier = if (qualifier.node.elementType == rules[PebbleParser.RULE_function_call_expression])
                    qualifier.firstChild
                else qualifier
                val ref = identifier.reference
                if (ref != null) {
                    val resolved = ref.resolve()
                    if (resolved is PebbleComment) {
                        return PebbleComment.getImplicitVariable(resolved)
                    }
                    return resolved ?: qualifier.parent
                }
            }
        }
        return null
    }
}
