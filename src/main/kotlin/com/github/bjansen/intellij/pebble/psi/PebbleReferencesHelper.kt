package com.github.bjansen.intellij.pebble.psi

import com.github.bjansen.pebble.parser.PebbleLexer
import com.github.bjansen.pebble.parser.PebbleParser
import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.SuperMethodsSearch
import com.intellij.psi.util.PropertyUtil
import com.intellij.psi.util.PsiTreeUtil

object PebbleReferencesHelper {
    private fun isOverride(method: PsiMethod)
            = SuperMethodsSearch.search(method, null, true, false).findFirst() != null

    fun buildPsiTypeLookups(type: PsiType?, project: Project): Array<Any> {
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
        } else if (type is PsiArrayType) {
            val objectType = PsiType.getJavaLangObject(
                PsiManager.getInstance(project),
                GlobalSearchScope.allScope(project)
            )

            val objectLookups = arrayListOf(*buildPsiTypeLookups(objectType, project))
            objectLookups.add(
                LookupElementBuilder.create("length")
                    .withTypeText("int")
                    .withIcon(AllIcons.Nodes.Property)
            )

            return objectLookups.toTypedArray()
        }

        return emptyArray()
    }

    // TODO check signatures
    fun findMembersByName(clazz: PsiClass?, name: String, methodsOnly: Boolean = false): List<PsiElement> {
        if (clazz != null) {
            val capitalizedName = StringUtil.capitalizeWithJavaBeanConvention(name)

            for (prefix in listOf("get", "is", "has")) {
                for (method in clazz.findMethodsByName(prefix + capitalizedName, true)) {
                    if (method.parameterList.parametersCount == 0) {
                        return listOf(method)
                    }
                }
            }

            val methods = clazz.allMethods.filter { it.name == name }

            if (methods.isNotEmpty()) {
                return methods
            }

            return if (methodsOnly) emptyList<PsiField>()
                    else clazz.allFields.filter {
                        it.hasModifierProperty(PsiModifier.PUBLIC) && it.name == name
                    }
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
            val qualifier = prevLeaf.prevSibling?.lastChild

            if (qualifier != null) {
                val identifier = when (qualifier.node.elementType) {
                    PebbleParserDefinition.rules[PebbleParser.RULE_function_call_expression] -> qualifier.firstChild
                    PebbleParserDefinition.rules[PebbleParser.RULE_term] -> qualifier.firstChild
                    PebbleParserDefinition.rules[PebbleParser.RULE_parenthesized_expression] -> qualifier.firstChild
                    else -> qualifier
                }

                fun resolveReference(): PsiElement? {
                    if (identifier is PebbleLiteral) {
                        return identifier
                    }
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
