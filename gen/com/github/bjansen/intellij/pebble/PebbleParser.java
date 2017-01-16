// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.bjansen.intellij.pebble.psi.PebbleTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class PebbleParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == AUTOESCAPE_TAG) {
      r = autoescape_tag(b, 0);
    }
    else if (t == BLOCK_TAG) {
      r = block_tag(b, 0);
    }
    else if (t == BOOLEAN_LITERAL) {
      r = boolean_literal(b, 0);
    }
    else if (t == CACHE_TAG) {
      r = cache_tag(b, 0);
    }
    else if (t == COMMENT_DIRECTIVE) {
      r = comment_directive(b, 0);
    }
    else if (t == CUSTOM_TAG) {
      r = custom_tag(b, 0);
    }
    else if (t == ELSE_TAG) {
      r = else_tag(b, 0);
    }
    else if (t == ELSEIF_TAG) {
      r = elseif_tag(b, 0);
    }
    else if (t == ENDAUTOESCAPE_TAG) {
      r = endautoescape_tag(b, 0);
    }
    else if (t == ENDBLOCK_TAG) {
      r = endblock_tag(b, 0);
    }
    else if (t == ENDCACHE_TAG) {
      r = endcache_tag(b, 0);
    }
    else if (t == ENDFILTER_TAG) {
      r = endfilter_tag(b, 0);
    }
    else if (t == ENDFOR_TAG) {
      r = endfor_tag(b, 0);
    }
    else if (t == ENDIF_TAG) {
      r = endif_tag(b, 0);
    }
    else if (t == ENDMACRO_TAG) {
      r = endmacro_tag(b, 0);
    }
    else if (t == ENDPARALLEL_TAG) {
      r = endparallel_tag(b, 0);
    }
    else if (t == ENDVERBATIM_TAG) {
      r = endverbatim_tag(b, 0);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0, -1);
    }
    else if (t == EXTENDS_TAG) {
      r = extends_tag(b, 0);
    }
    else if (t == FILTER_TAG) {
      r = filter_tag(b, 0);
    }
    else if (t == FILTERS) {
      r = filters(b, 0);
    }
    else if (t == FLUSH_TAG) {
      r = flush_tag(b, 0);
    }
    else if (t == FOR_TAG) {
      r = for_tag(b, 0);
    }
    else if (t == FUNCTION_CALL_EXPRESSION) {
      r = function_call_expression(b, 0);
    }
    else if (t == FUNCTION_PARAMETERS) {
      r = function_parameters(b, 0);
    }
    else if (t == IDENTIFIER) {
      r = identifier(b, 0);
    }
    else if (t == IF_TAG) {
      r = if_tag(b, 0);
    }
    else if (t == IMPORT_TAG) {
      r = import_tag(b, 0);
    }
    else if (t == INCLUDE_TAG) {
      r = include_tag(b, 0);
    }
    else if (t == MACRO_TAG) {
      r = macro_tag(b, 0);
    }
    else if (t == MAP_ELEMENT) {
      r = map_element(b, 0);
    }
    else if (t == NUMERIC_LITERAL) {
      r = numeric_literal(b, 0);
    }
    else if (t == PARALLEL_TAG) {
      r = parallel_tag(b, 0);
    }
    else if (t == PRINT_DIRECTIVE) {
      r = print_directive(b, 0);
    }
    else if (t == QUALIFIED_EXPRESSION) {
      r = qualified_expression(b, 0);
    }
    else if (t == SET_TAG) {
      r = set_tag(b, 0);
    }
    else if (t == STRING_LITERAL) {
      r = string_literal(b, 0);
    }
    else if (t == TAG_DIRECTIVE) {
      r = tag_directive(b, 0);
    }
    else if (t == TERM) {
      r = term(b, 0);
    }
    else if (t == TEST) {
      r = test(b, 0);
    }
    else if (t == VERBATIM_TAG) {
      r = verbatim_tag(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return pebbleFile(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ADDITIVE_EXPRESSION, AND_EXPRESSION, ARRAY_EXPRESSION, ASSIGNMENT_EXPRESSION,
      COMPARISON_EXPRESSION, CONCAT_EXPRESSION, EXPRESSION, FUNCTION_CALL_EXPRESSION,
      MAP_EXPRESSION, MEMBER_EXPRESSION, MULTIPLICATIVE_EXPRESSION, OR_EXPRESSION,
      PARENTHESIZED_EXPRESSION, PIPE_EXPRESSION, PREFIX_EXPRESSION, QUALIFIED_EXPRESSION,
      RANGE_EXPRESSION, TERNARY_EXPRESSION, TEST_EXPRESSION),
  };

  /* ********************************************************** */
  // OP_PLUS | OP_MINUS
  static boolean additive_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_op")) return false;
    if (!nextTokenIs(b, "", OP_MINUS, OP_PLUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS);
    if (!r) r = consumeToken(b, OP_MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "autoescape" (boolean_literal|string_literal) TAG_CLOSE
  //     item_*
  //     endautoescape_tag
  public static boolean autoescape_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoescape_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "autoescape");
    r = r && autoescape_tag_2(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && autoescape_tag_4(b, l + 1);
    r = r && endautoescape_tag(b, l + 1);
    exit_section_(b, m, AUTOESCAPE_TAG, r);
    return r;
  }

  // boolean_literal|string_literal
  private static boolean autoescape_tag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoescape_tag_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = boolean_literal(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // item_*
  private static boolean autoescape_tag_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoescape_tag_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "autoescape_tag_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // TAG_OPEN "block" (identifier|string_literal) TAG_CLOSE
  //     item_*
  //     endblock_tag
  public static boolean block_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "block");
    r = r && block_tag_2(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && block_tag_4(b, l + 1);
    r = r && endblock_tag(b, l + 1);
    exit_section_(b, m, BLOCK_TAG, r);
    return r;
  }

  // identifier|string_literal
  private static boolean block_tag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_tag_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // item_*
  private static boolean block_tag_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_tag_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "block_tag_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // TRUE | FALSE
  public static boolean boolean_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "boolean_literal")) return false;
    if (!nextTokenIs(b, "<boolean literal>", FALSE, TRUE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BOOLEAN_LITERAL, "<boolean literal>");
    r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "cache" expression TAG_CLOSE
  //     item_*
  //     endcache_tag
  public static boolean cache_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cache_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "cache");
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && cache_tag_4(b, l + 1);
    r = r && endcache_tag(b, l + 1);
    exit_section_(b, m, CACHE_TAG, r);
    return r;
  }

  // item_*
  private static boolean cache_tag_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cache_tag_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cache_tag_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // COMMENT
  public static boolean comment_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_directive")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    exit_section_(b, m, COMMENT_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // OP_EQ | EQUALS | OP_NEQ | OP_LE | OP_LT | OP_GE | OP_GT
  static boolean comparison_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparison_op")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_EQ);
    if (!r) r = consumeToken(b, EQUALS);
    if (!r) r = consumeToken(b, OP_NEQ);
    if (!r) r = consumeToken(b, OP_LE);
    if (!r) r = consumeToken(b, OP_LT);
    if (!r) r = consumeToken(b, OP_GE);
    if (!r) r = consumeToken(b, OP_GT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN CUSTOM_TAG_NAME expression? TAG_CLOSE
  public static boolean custom_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TAG_OPEN, CUSTOM_TAG_NAME);
    r = r && custom_tag_2(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, CUSTOM_TAG, r);
    return r;
  }

  // expression?
  private static boolean custom_tag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_tag_2")) return false;
    expression(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // TAG_OPEN "else" TAG_CLOSE item_*
  public static boolean else_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "else");
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && else_tag_3(b, l + 1);
    exit_section_(b, m, ELSE_TAG, r);
    return r;
  }

  // item_*
  private static boolean else_tag_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_tag_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "else_tag_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // TAG_OPEN "elseif" expression TAG_CLOSE item_*
  public static boolean elseif_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseif_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "elseif");
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && elseif_tag_4(b, l + 1);
    exit_section_(b, m, ELSEIF_TAG, r);
    return r;
  }

  // item_*
  private static boolean elseif_tag_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseif_tag_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "elseif_tag_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // TAG_OPEN "endautoescape" TAG_CLOSE
  public static boolean endautoescape_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endautoescape_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endautoescape");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDAUTOESCAPE_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endblock" (identifier|string_literal)? TAG_CLOSE
  public static boolean endblock_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endblock_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endblock");
    r = r && endblock_tag_2(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDBLOCK_TAG, r);
    return r;
  }

  // (identifier|string_literal)?
  private static boolean endblock_tag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endblock_tag_2")) return false;
    endblock_tag_2_0(b, l + 1);
    return true;
  }

  // identifier|string_literal
  private static boolean endblock_tag_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endblock_tag_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endcache" TAG_CLOSE
  public static boolean endcache_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endcache_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endcache");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDCACHE_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endfilter" TAG_CLOSE
  public static boolean endfilter_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endfilter_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endfilter");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDFILTER_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endfor" TAG_CLOSE
  public static boolean endfor_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endfor_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endfor");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDFOR_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endif" TAG_CLOSE
  public static boolean endif_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endif_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endif");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDIF_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endmacro" TAG_CLOSE
  public static boolean endmacro_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endmacro_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endmacro");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDMACRO_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endparallel" TAG_CLOSE
  public static boolean endparallel_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endparallel_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endparallel");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDPARALLEL_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "endverbatim" TAG_CLOSE
  public static boolean endverbatim_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endverbatim_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "endverbatim");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, ENDVERBATIM_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "extends" expression TAG_CLOSE
  public static boolean extends_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extends_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "extends");
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, EXTENDS_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "filter" expression ('|' expression)* TAG_CLOSE
  //     item_*
  //     endfilter_tag
  public static boolean filter_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filter_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "filter");
    r = r && expression(b, l + 1, -1);
    r = r && filter_tag_3(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && filter_tag_5(b, l + 1);
    r = r && endfilter_tag(b, l + 1);
    exit_section_(b, m, FILTER_TAG, r);
    return r;
  }

  // ('|' expression)*
  private static boolean filter_tag_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filter_tag_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!filter_tag_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "filter_tag_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // '|' expression
  private static boolean filter_tag_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filter_tag_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PIPE);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // item_*
  private static boolean filter_tag_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filter_tag_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "filter_tag_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // (OP_PIPE function_call_expression)*
  public static boolean filters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filters")) return false;
    Marker m = enter_section_(b, l, _NONE_, FILTERS, "<filters>");
    int c = current_position_(b);
    while (true) {
      if (!filters_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "filters", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // OP_PIPE function_call_expression
  private static boolean filters_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filters_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PIPE);
    r = r && function_call_expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "flush" TAG_CLOSE
  public static boolean flush_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "flush_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "flush");
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, FLUSH_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "for" identifier "in" expression TAG_CLOSE
  //     item_*
  //     else_tag?
  //     endfor_tag
  public static boolean for_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "for");
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, "in");
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && for_tag_6(b, l + 1);
    r = r && for_tag_7(b, l + 1);
    r = r && endfor_tag(b, l + 1);
    exit_section_(b, m, FOR_TAG, r);
    return r;
  }

  // item_*
  private static boolean for_tag_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_tag_6")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "for_tag_6", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // else_tag?
  private static boolean for_tag_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_tag_7")) return false;
    else_tag(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // identifier function_parameters
  public static boolean function_call_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_expression")) return false;
    if (!nextTokenIs(b, ID_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && function_parameters(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // LPAREN (expression (COMMA expression)*)? RPAREN
  public static boolean function_parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_parameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && function_parameters_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, FUNCTION_PARAMETERS, r);
    return r;
  }

  // (expression (COMMA expression)*)?
  private static boolean function_parameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_parameters_1")) return false;
    function_parameters_1_0(b, l + 1);
    return true;
  }

  // expression (COMMA expression)*
  private static boolean function_parameters_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_parameters_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1, -1);
    r = r && function_parameters_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA expression)*
  private static boolean function_parameters_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_parameters_1_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!function_parameters_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_parameters_1_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA expression
  private static boolean function_parameters_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_parameters_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID_NAME
  public static boolean identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier")) return false;
    if (!nextTokenIs(b, ID_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID_NAME);
    exit_section_(b, m, IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "if" expression TAG_CLOSE
  //     item_*
  //     elseif_tag*
  //     else_tag?
  //     endif_tag
  public static boolean if_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "if");
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && if_tag_4(b, l + 1);
    r = r && if_tag_5(b, l + 1);
    r = r && if_tag_6(b, l + 1);
    r = r && endif_tag(b, l + 1);
    exit_section_(b, m, IF_TAG, r);
    return r;
  }

  // item_*
  private static boolean if_tag_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_tag_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_tag_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // elseif_tag*
  private static boolean if_tag_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_tag_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!elseif_tag(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_tag_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // else_tag?
  private static boolean if_tag_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_tag_6")) return false;
    else_tag(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_OPEN "import" expression TAG_CLOSE
  public static boolean import_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "import");
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, IMPORT_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "include" expression ("with" expression)? TAG_CLOSE
  public static boolean include_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "include");
    r = r && expression(b, l + 1, -1);
    r = r && include_tag_3(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, INCLUDE_TAG, r);
    return r;
  }

  // ("with" expression)?
  private static boolean include_tag_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_tag_3")) return false;
    include_tag_3_0(b, l + 1);
    return true;
  }

  // "with" expression
  private static boolean include_tag_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_tag_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "with");
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // print_directive|comment_directive|tag_directive|CRLF|CONTENT
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = print_directive(b, l + 1);
    if (!r) r = comment_directive(b, l + 1);
    if (!r) r = tag_directive(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "macro" identifier function_parameters TAG_CLOSE
  //     item_*
  //     endmacro_tag
  public static boolean macro_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "macro");
    r = r && identifier(b, l + 1);
    r = r && function_parameters(b, l + 1);
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && macro_tag_5(b, l + 1);
    r = r && endmacro_tag(b, l + 1);
    exit_section_(b, m, MACRO_TAG, r);
    return r;
  }

  // item_*
  private static boolean macro_tag_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_tag_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "macro_tag_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // string_literal OP_COLON expression
  public static boolean map_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_element")) return false;
    if (!nextTokenIs(b, "<map element>", SINGLE_QUOTED_STRING, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MAP_ELEMENT, "<map element>");
    r = string_literal(b, l + 1);
    r = r && consumeToken(b, OP_COLON);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // OP_DIV | OP_MULT | OP_MOD
  static boolean multiplicative_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiplicative_op")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_DIV);
    if (!r) r = consumeToken(b, OP_MULT);
    if (!r) r = consumeToken(b, OP_MOD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NUMERIC
  public static boolean numeric_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "numeric_literal")) return false;
    if (!nextTokenIs(b, NUMERIC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMERIC);
    exit_section_(b, m, NUMERIC_LITERAL, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "parallel" TAG_CLOSE
  //     item_*
  //     endparallel_tag
  public static boolean parallel_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parallel_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "parallel");
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && parallel_tag_3(b, l + 1);
    r = r && endparallel_tag(b, l + 1);
    exit_section_(b, m, PARALLEL_TAG, r);
    return r;
  }

  // item_*
  private static boolean parallel_tag_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parallel_tag_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "parallel_tag_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // item_*
  static boolean pebbleFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pebbleFile")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "pebbleFile", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // VAR_OPEN expression filters VAR_CLOSE
  public static boolean print_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "print_directive")) return false;
    if (!nextTokenIs(b, VAR_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VAR_OPEN);
    r = r && expression(b, l + 1, -1);
    r = r && filters(b, l + 1);
    r = r && consumeToken(b, VAR_CLOSE);
    exit_section_(b, m, PRINT_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // identifier ('.' (identifier !'(' | function_call_expression))+
  public static boolean qualified_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_expression")) return false;
    if (!nextTokenIs(b, ID_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && qualified_expression_1(b, l + 1);
    exit_section_(b, m, QUALIFIED_EXPRESSION, r);
    return r;
  }

  // ('.' (identifier !'(' | function_call_expression))+
  private static boolean qualified_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = qualified_expression_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!qualified_expression_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "qualified_expression_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' (identifier !'(' | function_call_expression)
  private static boolean qualified_expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && qualified_expression_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // identifier !'(' | function_call_expression
  private static boolean qualified_expression_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_expression_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = qualified_expression_1_0_1_0(b, l + 1);
    if (!r) r = function_call_expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // identifier !'('
  private static boolean qualified_expression_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_expression_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && qualified_expression_1_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !'('
  private static boolean qualified_expression_1_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_expression_1_0_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, LPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "set" identifier OP_ASSIGN expression TAG_CLOSE
  public static boolean set_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "set");
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, OP_ASSIGN);
    r = r && expression(b, l + 1, -1);
    r = r && consumeToken(b, TAG_CLOSE);
    exit_section_(b, m, SET_TAG, r);
    return r;
  }

  /* ********************************************************** */
  // STRING | SINGLE_QUOTED_STRING
  public static boolean string_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_literal")) return false;
    if (!nextTokenIs(b, "<string literal>", SINGLE_QUOTED_STRING, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRING_LITERAL, "<string literal>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, SINGLE_QUOTED_STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // verbatim_tag
  //     | autoescape_tag
  //     | block_tag
  //     | cache_tag
  //     | extends_tag
  //     | filter_tag
  //     | flush_tag
  //     | for_tag
  //     | if_tag
  //     | import_tag
  //     | include_tag
  //     | macro_tag
  //     | parallel_tag
  //     | set_tag
  //     | custom_tag
  public static boolean tag_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_directive")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = verbatim_tag(b, l + 1);
    if (!r) r = autoescape_tag(b, l + 1);
    if (!r) r = block_tag(b, l + 1);
    if (!r) r = cache_tag(b, l + 1);
    if (!r) r = extends_tag(b, l + 1);
    if (!r) r = filter_tag(b, l + 1);
    if (!r) r = flush_tag(b, l + 1);
    if (!r) r = for_tag(b, l + 1);
    if (!r) r = if_tag(b, l + 1);
    if (!r) r = import_tag(b, l + 1);
    if (!r) r = include_tag(b, l + 1);
    if (!r) r = macro_tag(b, l + 1);
    if (!r) r = parallel_tag(b, l + 1);
    if (!r) r = set_tag(b, l + 1);
    if (!r) r = custom_tag(b, l + 1);
    exit_section_(b, m, TAG_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // boolean_literal | NULL | NONE | string_literal | numeric_literal | identifier
  public static boolean term(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERM, "<term>");
    r = boolean_literal(b, l + 1);
    if (!r) r = consumeToken(b, NULL);
    if (!r) r = consumeToken(b, NONE);
    if (!r) r = string_literal(b, l + 1);
    if (!r) r = numeric_literal(b, l + 1);
    if (!r) r = identifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NOT? (NULL | identifier)
  public static boolean test(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "test")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TEST, "<test>");
    r = test_0(b, l + 1);
    r = r && test_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NOT?
  private static boolean test_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "test_0")) return false;
    consumeToken(b, NOT);
    return true;
  }

  // NULL | identifier
  private static boolean test_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "test_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NULL);
    if (!r) r = identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OP_PLUS | OP_MINUS | NOT
  static boolean unary_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_op")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_PLUS);
    if (!r) r = consumeToken(b, OP_MINUS);
    if (!r) r = consumeToken(b, NOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_OPEN "verbatim" TAG_CLOSE
  //     (CRLF|CONTENT)*
  //     endverbatim_tag
  public static boolean verbatim_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "verbatim_tag")) return false;
    if (!nextTokenIs(b, TAG_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_OPEN);
    r = r && consumeToken(b, "verbatim");
    r = r && consumeToken(b, TAG_CLOSE);
    r = r && verbatim_tag_3(b, l + 1);
    r = r && endverbatim_tag(b, l + 1);
    exit_section_(b, m, VERBATIM_TAG, r);
    return r;
  }

  // (CRLF|CONTENT)*
  private static boolean verbatim_tag_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "verbatim_tag_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!verbatim_tag_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "verbatim_tag_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // CRLF|CONTENT
  private static boolean verbatim_tag_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "verbatim_tag_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: BINARY(assignment_expression)
  // 1: PREFIX(prefix_expression)
  // 2: PREFIX(parenthesized_expression)
  // 3: ATOM(array_expression)
  // 4: ATOM(map_expression)
  // 5: BINARY(or_expression)
  // 6: BINARY(and_expression)
  // 7: POSTFIX(test_expression)
  // 8: BINARY(comparison_expression)
  // 9: BINARY(ternary_expression)
  // 10: BINARY(additive_expression)
  // 11: BINARY(multiplicative_expression)
  // 12: BINARY(pipe_expression)
  // 13: BINARY(concat_expression)
  // 14: BINARY(range_expression)
  // 15: ATOM(member_expression)
  public static boolean expression(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = prefix_expression(b, l + 1);
    if (!r) r = parenthesized_expression(b, l + 1);
    if (!r) r = array_expression(b, l + 1);
    if (!r) r = map_expression(b, l + 1);
    if (!r) r = member_expression(b, l + 1);
    p = r;
    r = r && expression_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expression_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && consumeTokenSmart(b, OP_ASSIGN)) {
        r = expression(b, l, -1);
        exit_section_(b, l, m, ASSIGNMENT_EXPRESSION, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, OR)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, OR_EXPRESSION, r, true, null);
      }
      else if (g < 6 && consumeTokenSmart(b, AND)) {
        r = expression(b, l, 6);
        exit_section_(b, l, m, AND_EXPRESSION, r, true, null);
      }
      else if (g < 7 && test_expression_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, TEST_EXPRESSION, r, true, null);
      }
      else if (g < 8 && comparison_op(b, l + 1)) {
        r = expression(b, l, 8);
        exit_section_(b, l, m, COMPARISON_EXPRESSION, r, true, null);
      }
      else if (g < 9 && consumeTokenSmart(b, OP_TERNARY)) {
        r = report_error_(b, expression(b, l, 9));
        r = ternary_expression_1(b, l + 1) && r;
        exit_section_(b, l, m, TERNARY_EXPRESSION, r, true, null);
      }
      else if (g < 10 && additive_op(b, l + 1)) {
        r = expression(b, l, 10);
        exit_section_(b, l, m, ADDITIVE_EXPRESSION, r, true, null);
      }
      else if (g < 11 && multiplicative_op(b, l + 1)) {
        r = expression(b, l, 11);
        exit_section_(b, l, m, MULTIPLICATIVE_EXPRESSION, r, true, null);
      }
      else if (g < 12 && consumeTokenSmart(b, OP_PIPE)) {
        r = expression(b, l, 12);
        exit_section_(b, l, m, PIPE_EXPRESSION, r, true, null);
      }
      else if (g < 13 && consumeTokenSmart(b, "~")) {
        r = expression(b, l, 13);
        exit_section_(b, l, m, CONCAT_EXPRESSION, r, true, null);
      }
      else if (g < 14 && consumeTokenSmart(b, "..")) {
        r = expression(b, l, 14);
        exit_section_(b, l, m, RANGE_EXPRESSION, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  public static boolean prefix_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prefix_expression")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_op(b, l + 1);
    p = r;
    r = p && expression(b, l, 1);
    exit_section_(b, l, m, PREFIX_EXPRESSION, r, p, null);
    return r || p;
  }

  public static boolean parenthesized_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_expression")) return false;
    if (!nextTokenIsSmart(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r;
    r = p && expression(b, l, 2);
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    exit_section_(b, l, m, PARENTHESIZED_EXPRESSION, r, p, null);
    return r || p;
  }

  // '[' (expression (',' expression)*)? ']'
  public static boolean array_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression")) return false;
    if (!nextTokenIsSmart(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LBRACKET);
    r = r && array_expression_1(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, ARRAY_EXPRESSION, r);
    return r;
  }

  // (expression (',' expression)*)?
  private static boolean array_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression_1")) return false;
    array_expression_1_0(b, l + 1);
    return true;
  }

  // expression (',' expression)*
  private static boolean array_expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1, -1);
    r = r && array_expression_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' expression)*
  private static boolean array_expression_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression_1_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!array_expression_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "array_expression_1_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' expression
  private static boolean array_expression_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_expression_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, COMMA);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{' (map_element (',' map_element)*)? '}'
  public static boolean map_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_expression")) return false;
    if (!nextTokenIsSmart(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LBRACE);
    r = r && map_expression_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, MAP_EXPRESSION, r);
    return r;
  }

  // (map_element (',' map_element)*)?
  private static boolean map_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_expression_1")) return false;
    map_expression_1_0(b, l + 1);
    return true;
  }

  // map_element (',' map_element)*
  private static boolean map_expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = map_element(b, l + 1);
    r = r && map_expression_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' map_element)*
  private static boolean map_expression_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_expression_1_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!map_expression_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "map_expression_1_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' map_element
  private static boolean map_expression_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_expression_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, COMMA);
    r = r && map_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (IS test)|(CONTAINS expression)
  private static boolean test_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "test_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = test_expression_0_0(b, l + 1);
    if (!r) r = test_expression_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // IS test
  private static boolean test_expression_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "test_expression_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, IS);
    r = r && test(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CONTAINS expression
  private static boolean test_expression_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "test_expression_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, CONTAINS);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // OP_COLON expression
  private static boolean ternary_expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ternary_expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_COLON);
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // function_call_expression
  //   | qualified_expression
  //   | term
  public static boolean member_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "member_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, MEMBER_EXPRESSION, "<member expression>");
    r = function_call_expression(b, l + 1);
    if (!r) r = qualified_expression(b, l + 1);
    if (!r) r = term(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
