// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PebbleForTag extends PebbleTag {

  @NotNull
  List<PebbleCommentDirective> getCommentDirectiveList();

  @Nullable
  PebbleElseTag getElseTag();

  @NotNull
  PebbleEndforTag getEndforTag();

  @NotNull
  PebbleExpression getExpression();

  @NotNull
  PebbleIdentifier getIdentifier();

  @NotNull
  List<PebblePrintDirective> getPrintDirectiveList();

  @NotNull
  List<PebbleTagDirective> getTagDirectiveList();

}
