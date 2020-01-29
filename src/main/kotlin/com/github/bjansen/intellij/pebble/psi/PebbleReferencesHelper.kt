package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.pebble.parser.PebbleLexer
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.*
import com.intellij.psi.search.searches.SuperMethodsSearch
import com.intellij.psi.util.PropertyUtil
import com.intellij.psi.util.PsiTreeUtil

object PebbleReferencesHelper {
    private fun isOverride(method: PsiMethod)
            = SuperMethodsSearch.search(method, null, true, false).findFirst() != null

    fun buildPsiTypeLookups(type: PsiType?): Array<Any> {
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

    fun findMembersByName(clazz: PsiClass?, name: String, methodsOnly: Boolean = false): List<PsiElement> {
        if (clazz != null) {
            // TODO check signatures
            val methods = clazz.allMethods.filter {
                getPropertyNameAndType(it)?.first == name || it.name == name
            }

            val fields =
                    if (methodsOnly) emptyList<PsiField>()
                    else clazz.fields.filter {
                        it.hasModifierProperty(PsiModifier.PUBLIC) && it.name == name
                    }

            return methods.plus(fields)
        }

        return emptyList()
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

    fun findQualifyingMember(psi: PsiElement): PsiElement? {
        val prevLeaf = PsiTreeUtil.prevVisibleLeaf(psi)

        if (prevLeaf != null && prevLeaf.node.elementType == PebbleParserDefinition.tokens[PebbleLexer.OP_MEMBER]) {
            val qualifier = prevLeaf.prevSibling

            if (qualifier != null) {
                val identifier = if (qualifier.node.elementType == PebbleParserDefinition.rules[PebbleParser.RULE_function_call_expression])
                    qualifier.firstChild
                else qualifier

                fun resolveReference(): PsiElement? {
                    identifier.references.forEach {
                        val resolved = it.resolve()
                        if (resolved != null) {
                            return resolved
                        }
                    }

                    return null
                }

                val resolved = resolveReference()
                if (resolved is PebbleComment) {
                    return PebbleComment.getImplicitVariable(resolved)
                }
                return resolved ?: qualifier.parent
            }
        }
        return null
    }
}
