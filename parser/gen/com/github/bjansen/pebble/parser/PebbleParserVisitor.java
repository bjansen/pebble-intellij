// Generated from /Users/bastien/IdeaProjects/pebble-intellij/parser/grammar/com/github/bjansen/pebble/parser/PebbleParser.g4 by ANTLR 4.6
package com.github.bjansen.pebble.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PebbleParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PebbleParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PebbleParser#pebbleTemplate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPebbleTemplate(PebbleParser.PebbleTemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#printDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintDirective(PebbleParser.PrintDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#commentDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommentDirective(PebbleParser.CommentDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#tagDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagDirective(PebbleParser.TagDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#verbatimTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVerbatimTag(PebbleParser.VerbatimTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#genericTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericTag(PebbleParser.GenericTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(PebbleParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(PebbleParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#parenthesized_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesized_expression(PebbleParser.Parenthesized_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#array_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_expression(PebbleParser.Array_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#map_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap_expression(PebbleParser.Map_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#map_element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap_element(PebbleParser.Map_elementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#member_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember_expression(PebbleParser.Member_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#qualified_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualified_expression(PebbleParser.Qualified_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#function_call_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_expression(PebbleParser.Function_call_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#function_parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_parameters(PebbleParser.Function_parametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(PebbleParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(PebbleParser.TestContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#unary_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_op(PebbleParser.Unary_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#multiplicative_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_op(PebbleParser.Multiplicative_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#additive_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_op(PebbleParser.Additive_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#comparison_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison_op(PebbleParser.Comparison_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#boolean_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_literal(PebbleParser.Boolean_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#string_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_literal(PebbleParser.String_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#numeric_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric_literal(PebbleParser.Numeric_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(PebbleParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link PebbleParser#filters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilters(PebbleParser.FiltersContext ctx);
}