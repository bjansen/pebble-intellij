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

public class PebbleTermImpl extends ASTWrapperPsiElement implements PebbleTerm {

  public PebbleTermImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PebbleVisitor visitor) {
    visitor.visitTerm(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PebbleVisitor) accept((PebbleVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PebbleBooleanLiteral getBooleanLiteral() {
    return findChildByClass(PebbleBooleanLiteral.class);
  }

  @Override
  @Nullable
  public PebbleIdentifier getIdentifier() {
    return findChildByClass(PebbleIdentifier.class);
  }

  @Override
  @Nullable
  public PebbleNumericLiteral getNumericLiteral() {
    return findChildByClass(PebbleNumericLiteral.class);
  }

  @Override
  @Nullable
  public PebbleStringLiteral getStringLiteral() {
    return findChildByClass(PebbleStringLiteral.class);
  }

}
