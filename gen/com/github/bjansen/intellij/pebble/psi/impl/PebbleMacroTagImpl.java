// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.bjansen.intellij.pebble.psi.PebbleTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.bjansen.intellij.pebble.psi.*;

public class PebbleMacroTagImpl extends ASTWrapperPsiElement implements PebbleMacroTag {

  public PebbleMacroTagImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PebbleVisitor visitor) {
    visitor.visitMacroTag(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PebbleVisitor) accept((PebbleVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PebbleCommentDirective> getCommentDirectiveList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PebbleCommentDirective.class);
  }

  @Override
  @NotNull
  public PebbleEndmacroTag getEndmacroTag() {
    return findNotNullChildByClass(PebbleEndmacroTag.class);
  }

  @Override
  @NotNull
  public PebbleFunctionParameters getFunctionParameters() {
    return findNotNullChildByClass(PebbleFunctionParameters.class);
  }

  @Override
  @NotNull
  public PebbleIdentifier getIdentifier() {
    return findNotNullChildByClass(PebbleIdentifier.class);
  }

  @Override
  @NotNull
  public List<PebblePrintDirective> getPrintDirectiveList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PebblePrintDirective.class);
  }

  @Override
  @NotNull
  public List<PebbleTagDirective> getTagDirectiveList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PebbleTagDirective.class);
  }

}
