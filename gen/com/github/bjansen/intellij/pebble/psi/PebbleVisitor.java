// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class PebbleVisitor extends PsiElementVisitor {

  public void visitAdditiveExpression(@NotNull PebbleAdditiveExpression o) {
    visitExpression(o);
  }

  public void visitAndExpression(@NotNull PebbleAndExpression o) {
    visitExpression(o);
  }

  public void visitArrayExpression(@NotNull PebbleArrayExpression o) {
    visitExpression(o);
  }

  public void visitAssignmentExpression(@NotNull PebbleAssignmentExpression o) {
    visitExpression(o);
  }

  public void visitAutoescapeTag(@NotNull PebbleAutoescapeTag o) {
    visitTag(o);
  }

  public void visitBlockTag(@NotNull PebbleBlockTag o) {
    visitTag(o);
  }

  public void visitBooleanLiteral(@NotNull PebbleBooleanLiteral o) {
    visitPsiElement(o);
  }

  public void visitCacheTag(@NotNull PebbleCacheTag o) {
    visitTag(o);
  }

  public void visitCommentDirective(@NotNull PebbleCommentDirective o) {
    visitPsiElement(o);
  }

  public void visitComparisonExpression(@NotNull PebbleComparisonExpression o) {
    visitExpression(o);
  }

  public void visitConcatExpression(@NotNull PebbleConcatExpression o) {
    visitExpression(o);
  }

  public void visitCustomTag(@NotNull PebbleCustomTag o) {
    visitTag(o);
  }

  public void visitElseTag(@NotNull PebbleElseTag o) {
    visitTag(o);
  }

  public void visitElseifTag(@NotNull PebbleElseifTag o) {
    visitTag(o);
  }

  public void visitEndautoescapeTag(@NotNull PebbleEndautoescapeTag o) {
    visitTag(o);
  }

  public void visitEndblockTag(@NotNull PebbleEndblockTag o) {
    visitTag(o);
  }

  public void visitEndcacheTag(@NotNull PebbleEndcacheTag o) {
    visitTag(o);
  }

  public void visitEndfilterTag(@NotNull PebbleEndfilterTag o) {
    visitTag(o);
  }

  public void visitEndforTag(@NotNull PebbleEndforTag o) {
    visitTag(o);
  }

  public void visitEndifTag(@NotNull PebbleEndifTag o) {
    visitTag(o);
  }

  public void visitEndmacroTag(@NotNull PebbleEndmacroTag o) {
    visitTag(o);
  }

  public void visitEndparallelTag(@NotNull PebbleEndparallelTag o) {
    visitTag(o);
  }

  public void visitEndverbatimTag(@NotNull PebbleEndverbatimTag o) {
    visitTag(o);
  }

  public void visitExpression(@NotNull PebbleExpression o) {
    visitPsiElement(o);
  }

  public void visitExtendsTag(@NotNull PebbleExtendsTag o) {
    visitTag(o);
  }

  public void visitFilterTag(@NotNull PebbleFilterTag o) {
    visitTag(o);
  }

  public void visitFilters(@NotNull PebbleFilters o) {
    visitPsiElement(o);
  }

  public void visitFlushTag(@NotNull PebbleFlushTag o) {
    visitTag(o);
  }

  public void visitForTag(@NotNull PebbleForTag o) {
    visitTag(o);
  }

  public void visitFunctionCallExpression(@NotNull PebbleFunctionCallExpression o) {
    visitExpression(o);
  }

  public void visitFunctionParameters(@NotNull PebbleFunctionParameters o) {
    visitPsiElement(o);
  }

  public void visitIdentifier(@NotNull PebbleIdentifier o) {
    visitPsiElement(o);
  }

  public void visitIfTag(@NotNull PebbleIfTag o) {
    visitTag(o);
  }

  public void visitImportTag(@NotNull PebbleImportTag o) {
    visitTag(o);
  }

  public void visitIncludeTag(@NotNull PebbleIncludeTag o) {
    visitTag(o);
  }

  public void visitMacroTag(@NotNull PebbleMacroTag o) {
    visitTag(o);
  }

  public void visitMapElement(@NotNull PebbleMapElement o) {
    visitPsiElement(o);
  }

  public void visitMapExpression(@NotNull PebbleMapExpression o) {
    visitExpression(o);
  }

  public void visitMemberExpression(@NotNull PebbleMemberExpression o) {
    visitExpression(o);
  }

  public void visitMultiplicativeExpression(@NotNull PebbleMultiplicativeExpression o) {
    visitExpression(o);
  }

  public void visitNumericLiteral(@NotNull PebbleNumericLiteral o) {
    visitPsiElement(o);
  }

  public void visitOrExpression(@NotNull PebbleOrExpression o) {
    visitExpression(o);
  }

  public void visitParallelTag(@NotNull PebbleParallelTag o) {
    visitTag(o);
  }

  public void visitParenthesizedExpression(@NotNull PebbleParenthesizedExpression o) {
    visitExpression(o);
  }

  public void visitPipeExpression(@NotNull PebblePipeExpression o) {
    visitExpression(o);
  }

  public void visitPrefixExpression(@NotNull PebblePrefixExpression o) {
    visitExpression(o);
  }

  public void visitPrintDirective(@NotNull PebblePrintDirective o) {
    visitPsiElement(o);
  }

  public void visitQualifiedExpression(@NotNull PebbleQualifiedExpression o) {
    visitExpression(o);
  }

  public void visitRangeExpression(@NotNull PebbleRangeExpression o) {
    visitExpression(o);
  }

  public void visitSetTag(@NotNull PebbleSetTag o) {
    visitTag(o);
  }

  public void visitStringLiteral(@NotNull PebbleStringLiteral o) {
    visitPsiElement(o);
  }

  public void visitTagDirective(@NotNull PebbleTagDirective o) {
    visitPsiElement(o);
  }

  public void visitTerm(@NotNull PebbleTerm o) {
    visitPsiElement(o);
  }

  public void visitTernaryExpression(@NotNull PebbleTernaryExpression o) {
    visitExpression(o);
  }

  public void visitTest(@NotNull PebbleTest o) {
    visitPsiElement(o);
  }

  public void visitTestExpression(@NotNull PebbleTestExpression o) {
    visitExpression(o);
  }

  public void visitVerbatimTag(@NotNull PebbleVerbatimTag o) {
    visitTag(o);
  }

  public void visitTag(@NotNull PebbleTag o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
