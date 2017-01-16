// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.bjansen.intellij.pebble.psi.impl.*;

public interface PebbleTypes {

  IElementType ADDITIVE_EXPRESSION = new PebbleElementType("ADDITIVE_EXPRESSION");
  IElementType AND_EXPRESSION = new PebbleElementType("AND_EXPRESSION");
  IElementType ARRAY_EXPRESSION = new PebbleElementType("ARRAY_EXPRESSION");
  IElementType ASSIGNMENT_EXPRESSION = new PebbleElementType("ASSIGNMENT_EXPRESSION");
  IElementType AUTOESCAPE_TAG = new PebbleElementType("AUTOESCAPE_TAG");
  IElementType BLOCK_TAG = new PebbleElementType("BLOCK_TAG");
  IElementType BOOLEAN_LITERAL = new PebbleElementType("BOOLEAN_LITERAL");
  IElementType CACHE_TAG = new PebbleElementType("CACHE_TAG");
  IElementType COMMENT_DIRECTIVE = new PebbleElementType("COMMENT_DIRECTIVE");
  IElementType COMPARISON_EXPRESSION = new PebbleElementType("COMPARISON_EXPRESSION");
  IElementType CONCAT_EXPRESSION = new PebbleElementType("CONCAT_EXPRESSION");
  IElementType CUSTOM_TAG = new PebbleElementType("CUSTOM_TAG");
  IElementType ELSEIF_TAG = new PebbleElementType("ELSEIF_TAG");
  IElementType ELSE_TAG = new PebbleElementType("ELSE_TAG");
  IElementType ENDAUTOESCAPE_TAG = new PebbleElementType("ENDAUTOESCAPE_TAG");
  IElementType ENDBLOCK_TAG = new PebbleElementType("ENDBLOCK_TAG");
  IElementType ENDCACHE_TAG = new PebbleElementType("ENDCACHE_TAG");
  IElementType ENDFILTER_TAG = new PebbleElementType("ENDFILTER_TAG");
  IElementType ENDFOR_TAG = new PebbleElementType("ENDFOR_TAG");
  IElementType ENDIF_TAG = new PebbleElementType("ENDIF_TAG");
  IElementType ENDMACRO_TAG = new PebbleElementType("ENDMACRO_TAG");
  IElementType ENDPARALLEL_TAG = new PebbleElementType("ENDPARALLEL_TAG");
  IElementType ENDVERBATIM_TAG = new PebbleElementType("ENDVERBATIM_TAG");
  IElementType EXPRESSION = new PebbleElementType("EXPRESSION");
  IElementType EXTENDS_TAG = new PebbleElementType("EXTENDS_TAG");
  IElementType FILTERS = new PebbleElementType("FILTERS");
  IElementType FILTER_TAG = new PebbleElementType("FILTER_TAG");
  IElementType FLUSH_TAG = new PebbleElementType("FLUSH_TAG");
  IElementType FOR_TAG = new PebbleElementType("FOR_TAG");
  IElementType FUNCTION_CALL_EXPRESSION = new PebbleElementType("FUNCTION_CALL_EXPRESSION");
  IElementType FUNCTION_PARAMETERS = new PebbleElementType("FUNCTION_PARAMETERS");
  IElementType IDENTIFIER = new PebbleElementType("IDENTIFIER");
  IElementType IF_TAG = new PebbleElementType("IF_TAG");
  IElementType IMPORT_TAG = new PebbleElementType("IMPORT_TAG");
  IElementType INCLUDE_TAG = new PebbleElementType("INCLUDE_TAG");
  IElementType MACRO_TAG = new PebbleElementType("MACRO_TAG");
  IElementType MAP_ELEMENT = new PebbleElementType("MAP_ELEMENT");
  IElementType MAP_EXPRESSION = new PebbleElementType("MAP_EXPRESSION");
  IElementType MEMBER_EXPRESSION = new PebbleElementType("MEMBER_EXPRESSION");
  IElementType MULTIPLICATIVE_EXPRESSION = new PebbleElementType("MULTIPLICATIVE_EXPRESSION");
  IElementType NUMERIC_LITERAL = new PebbleElementType("NUMERIC_LITERAL");
  IElementType OR_EXPRESSION = new PebbleElementType("OR_EXPRESSION");
  IElementType PARALLEL_TAG = new PebbleElementType("PARALLEL_TAG");
  IElementType PARENTHESIZED_EXPRESSION = new PebbleElementType("PARENTHESIZED_EXPRESSION");
  IElementType PIPE_EXPRESSION = new PebbleElementType("PIPE_EXPRESSION");
  IElementType PREFIX_EXPRESSION = new PebbleElementType("PREFIX_EXPRESSION");
  IElementType PRINT_DIRECTIVE = new PebbleElementType("PRINT_DIRECTIVE");
  IElementType QUALIFIED_EXPRESSION = new PebbleElementType("QUALIFIED_EXPRESSION");
  IElementType RANGE_EXPRESSION = new PebbleElementType("RANGE_EXPRESSION");
  IElementType SET_TAG = new PebbleElementType("SET_TAG");
  IElementType STRING_LITERAL = new PebbleElementType("STRING_LITERAL");
  IElementType TAG_DIRECTIVE = new PebbleElementType("TAG_DIRECTIVE");
  IElementType TERM = new PebbleElementType("TERM");
  IElementType TERNARY_EXPRESSION = new PebbleElementType("TERNARY_EXPRESSION");
  IElementType TEST = new PebbleElementType("TEST");
  IElementType TEST_EXPRESSION = new PebbleElementType("TEST_EXPRESSION");
  IElementType VERBATIM_TAG = new PebbleElementType("VERBATIM_TAG");

  IElementType AND = new PebbleTokenType("AND");
  IElementType COMMA = new PebbleTokenType(",");
  IElementType COMMENT = new PebbleTokenType("COMMENT");
  IElementType CONTAINS = new PebbleTokenType("CONTAINS");
  IElementType CONTENT = new PebbleTokenType("CONTENT");
  IElementType CRLF = new PebbleTokenType("CRLF");
  IElementType CUSTOM_TAG_NAME = new PebbleTokenType("CUSTOM_TAG_NAME");
  IElementType EQUALS = new PebbleTokenType("EQUALS");
  IElementType FALSE = new PebbleTokenType("false");
  IElementType ID_NAME = new PebbleTokenType("ID_NAME");
  IElementType IS = new PebbleTokenType("is");
  IElementType LBRACE = new PebbleTokenType("{");
  IElementType LBRACKET = new PebbleTokenType("[");
  IElementType LPAREN = new PebbleTokenType("(");
  IElementType NONE = new PebbleTokenType("NONE");
  IElementType NOT = new PebbleTokenType("NOT");
  IElementType NULL = new PebbleTokenType("NULL");
  IElementType NUMERIC = new PebbleTokenType("NUMERIC");
  IElementType OP_ASSIGN = new PebbleTokenType("=");
  IElementType OP_DIV = new PebbleTokenType("OP_DIV");
  IElementType OP_EQ = new PebbleTokenType("==");
  IElementType OP_GE = new PebbleTokenType("OP_GE");
  IElementType OP_GT = new PebbleTokenType("OP_GT");
  IElementType OP_LE = new PebbleTokenType("OP_LE");
  IElementType OP_LT = new PebbleTokenType("OP_LT");
  IElementType OP_MINUS = new PebbleTokenType("OP_MINUS");
  IElementType OP_MOD = new PebbleTokenType("OP_MOD");
  IElementType OP_MULT = new PebbleTokenType("OP_MULT");
  IElementType OP_NEQ = new PebbleTokenType("OP_NEQ");
  IElementType OP_PIPE = new PebbleTokenType("|");
  IElementType OP_PLUS = new PebbleTokenType("OP_PLUS");
  IElementType OR = new PebbleTokenType("OR");
  IElementType RBRACE = new PebbleTokenType("}");
  IElementType RBRACKET = new PebbleTokenType("]");
  IElementType RPAREN = new PebbleTokenType(")");
  IElementType SINGLE_QUOTED_STRING = new PebbleTokenType("SINGLE_QUOTED_STRING");
  IElementType STRING = new PebbleTokenType("STRING");
  IElementType TAG_CLOSE = new PebbleTokenType("%}");
  IElementType TAG_OPEN = new PebbleTokenType("{%");
  IElementType TRUE = new PebbleTokenType("true");
  IElementType VAR_CLOSE = new PebbleTokenType("}}");
  IElementType VAR_OPEN = new PebbleTokenType("{{");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ADDITIVE_EXPRESSION) {
        return new PebbleAdditiveExpressionImpl(node);
      }
      else if (type == AND_EXPRESSION) {
        return new PebbleAndExpressionImpl(node);
      }
      else if (type == ARRAY_EXPRESSION) {
        return new PebbleArrayExpressionImpl(node);
      }
      else if (type == ASSIGNMENT_EXPRESSION) {
        return new PebbleAssignmentExpressionImpl(node);
      }
      else if (type == AUTOESCAPE_TAG) {
        return new PebbleAutoescapeTagImpl(node);
      }
      else if (type == BLOCK_TAG) {
        return new PebbleBlockTagImpl(node);
      }
      else if (type == BOOLEAN_LITERAL) {
        return new PebbleBooleanLiteralImpl(node);
      }
      else if (type == CACHE_TAG) {
        return new PebbleCacheTagImpl(node);
      }
      else if (type == COMMENT_DIRECTIVE) {
        return new PebbleCommentDirectiveImpl(node);
      }
      else if (type == COMPARISON_EXPRESSION) {
        return new PebbleComparisonExpressionImpl(node);
      }
      else if (type == CONCAT_EXPRESSION) {
        return new PebbleConcatExpressionImpl(node);
      }
      else if (type == CUSTOM_TAG) {
        return new PebbleCustomTagImpl(node);
      }
      else if (type == ELSEIF_TAG) {
        return new PebbleElseifTagImpl(node);
      }
      else if (type == ELSE_TAG) {
        return new PebbleElseTagImpl(node);
      }
      else if (type == ENDAUTOESCAPE_TAG) {
        return new PebbleEndautoescapeTagImpl(node);
      }
      else if (type == ENDBLOCK_TAG) {
        return new PebbleEndblockTagImpl(node);
      }
      else if (type == ENDCACHE_TAG) {
        return new PebbleEndcacheTagImpl(node);
      }
      else if (type == ENDFILTER_TAG) {
        return new PebbleEndfilterTagImpl(node);
      }
      else if (type == ENDFOR_TAG) {
        return new PebbleEndforTagImpl(node);
      }
      else if (type == ENDIF_TAG) {
        return new PebbleEndifTagImpl(node);
      }
      else if (type == ENDMACRO_TAG) {
        return new PebbleEndmacroTagImpl(node);
      }
      else if (type == ENDPARALLEL_TAG) {
        return new PebbleEndparallelTagImpl(node);
      }
      else if (type == ENDVERBATIM_TAG) {
        return new PebbleEndverbatimTagImpl(node);
      }
      else if (type == EXTENDS_TAG) {
        return new PebbleExtendsTagImpl(node);
      }
      else if (type == FILTERS) {
        return new PebbleFiltersImpl(node);
      }
      else if (type == FILTER_TAG) {
        return new PebbleFilterTagImpl(node);
      }
      else if (type == FLUSH_TAG) {
        return new PebbleFlushTagImpl(node);
      }
      else if (type == FOR_TAG) {
        return new PebbleForTagImpl(node);
      }
      else if (type == FUNCTION_CALL_EXPRESSION) {
        return new PebbleFunctionCallExpressionImpl(node);
      }
      else if (type == FUNCTION_PARAMETERS) {
        return new PebbleFunctionParametersImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new PebbleIdentifierImpl(node);
      }
      else if (type == IF_TAG) {
        return new PebbleIfTagImpl(node);
      }
      else if (type == IMPORT_TAG) {
        return new PebbleImportTagImpl(node);
      }
      else if (type == INCLUDE_TAG) {
        return new PebbleIncludeTagImpl(node);
      }
      else if (type == MACRO_TAG) {
        return new PebbleMacroTagImpl(node);
      }
      else if (type == MAP_ELEMENT) {
        return new PebbleMapElementImpl(node);
      }
      else if (type == MAP_EXPRESSION) {
        return new PebbleMapExpressionImpl(node);
      }
      else if (type == MEMBER_EXPRESSION) {
        return new PebbleMemberExpressionImpl(node);
      }
      else if (type == MULTIPLICATIVE_EXPRESSION) {
        return new PebbleMultiplicativeExpressionImpl(node);
      }
      else if (type == NUMERIC_LITERAL) {
        return new PebbleNumericLiteralImpl(node);
      }
      else if (type == OR_EXPRESSION) {
        return new PebbleOrExpressionImpl(node);
      }
      else if (type == PARALLEL_TAG) {
        return new PebbleParallelTagImpl(node);
      }
      else if (type == PARENTHESIZED_EXPRESSION) {
        return new PebbleParenthesizedExpressionImpl(node);
      }
      else if (type == PIPE_EXPRESSION) {
        return new PebblePipeExpressionImpl(node);
      }
      else if (type == PREFIX_EXPRESSION) {
        return new PebblePrefixExpressionImpl(node);
      }
      else if (type == PRINT_DIRECTIVE) {
        return new PebblePrintDirectiveImpl(node);
      }
      else if (type == QUALIFIED_EXPRESSION) {
        return new PebbleQualifiedExpressionImpl(node);
      }
      else if (type == RANGE_EXPRESSION) {
        return new PebbleRangeExpressionImpl(node);
      }
      else if (type == SET_TAG) {
        return new PebbleSetTagImpl(node);
      }
      else if (type == STRING_LITERAL) {
        return new PebbleStringLiteralImpl(node);
      }
      else if (type == TAG_DIRECTIVE) {
        return new PebbleTagDirectiveImpl(node);
      }
      else if (type == TERM) {
        return new PebbleTermImpl(node);
      }
      else if (type == TERNARY_EXPRESSION) {
        return new PebbleTernaryExpressionImpl(node);
      }
      else if (type == TEST) {
        return new PebbleTestImpl(node);
      }
      else if (type == TEST_EXPRESSION) {
        return new PebbleTestExpressionImpl(node);
      }
      else if (type == VERBATIM_TAG) {
        return new PebbleVerbatimTagImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
