// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PebbleParallelTag extends PebbleTag {

  @NotNull
  List<PebbleCommentDirective> getCommentDirectiveList();

  @NotNull
  PebbleEndparallelTag getEndparallelTag();

  @NotNull
  List<PebblePrintDirective> getPrintDirectiveList();

  @NotNull
  List<PebbleTagDirective> getTagDirectiveList();

}
