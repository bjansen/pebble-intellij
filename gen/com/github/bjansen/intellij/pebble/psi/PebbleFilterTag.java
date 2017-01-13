// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PebbleFilterTag extends PebbleTag {

  @NotNull
  List<PebbleCommentDirective> getCommentDirectiveList();

  @NotNull
  PebbleEndfilterTag getEndfilterTag();

  @NotNull
  List<PebbleExpression> getExpressionList();

  @NotNull
  List<PebblePrintDirective> getPrintDirectiveList();

  @NotNull
  List<PebbleTagDirective> getTagDirectiveList();

}
