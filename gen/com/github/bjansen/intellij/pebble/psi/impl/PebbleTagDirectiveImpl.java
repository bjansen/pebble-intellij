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

public class PebbleTagDirectiveImpl extends ASTWrapperPsiElement implements PebbleTagDirective {

  public PebbleTagDirectiveImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PebbleVisitor visitor) {
    visitor.visitTagDirective(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PebbleVisitor) accept((PebbleVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PebbleAutoescapeTag getAutoescapeTag() {
    return findChildByClass(PebbleAutoescapeTag.class);
  }

  @Override
  @Nullable
  public PebbleBlockTag getBlockTag() {
    return findChildByClass(PebbleBlockTag.class);
  }

  @Override
  @Nullable
  public PebbleCacheTag getCacheTag() {
    return findChildByClass(PebbleCacheTag.class);
  }

  @Override
  @Nullable
  public PebbleCustomTag getCustomTag() {
    return findChildByClass(PebbleCustomTag.class);
  }

  @Override
  @Nullable
  public PebbleExtendsTag getExtendsTag() {
    return findChildByClass(PebbleExtendsTag.class);
  }

  @Override
  @Nullable
  public PebbleFilterTag getFilterTag() {
    return findChildByClass(PebbleFilterTag.class);
  }

  @Override
  @Nullable
  public PebbleFlushTag getFlushTag() {
    return findChildByClass(PebbleFlushTag.class);
  }

  @Override
  @Nullable
  public PebbleForTag getForTag() {
    return findChildByClass(PebbleForTag.class);
  }

  @Override
  @Nullable
  public PebbleIfTag getIfTag() {
    return findChildByClass(PebbleIfTag.class);
  }

  @Override
  @Nullable
  public PebbleImportTag getImportTag() {
    return findChildByClass(PebbleImportTag.class);
  }

  @Override
  @Nullable
  public PebbleIncludeTag getIncludeTag() {
    return findChildByClass(PebbleIncludeTag.class);
  }

  @Override
  @Nullable
  public PebbleMacroTag getMacroTag() {
    return findChildByClass(PebbleMacroTag.class);
  }

  @Override
  @Nullable
  public PebbleParallelTag getParallelTag() {
    return findChildByClass(PebbleParallelTag.class);
  }

  @Override
  @Nullable
  public PebbleSetTag getSetTag() {
    return findChildByClass(PebbleSetTag.class);
  }

  @Override
  @Nullable
  public PebbleVerbatimTag getVerbatimTag() {
    return findChildByClass(PebbleVerbatimTag.class);
  }

}
