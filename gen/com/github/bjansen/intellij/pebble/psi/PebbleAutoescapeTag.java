// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PebbleAutoescapeTag extends PebbleTag {

  @Nullable
  PebbleBooleanLiteral getBooleanLiteral();

  @NotNull
  List<PebbleCommentDirective> getCommentDirectiveList();

  @NotNull
  PebbleEndautoescapeTag getEndautoescapeTag();

  @NotNull
  List<PebblePrintDirective> getPrintDirectiveList();

  @Nullable
  PebbleStringLiteral getStringLiteral();

  @NotNull
  List<PebbleTagDirective> getTagDirectiveList();

}
