// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PebbleTagDirective extends PsiElement {

  @Nullable
  PebbleAutoescapeTag getAutoescapeTag();

  @Nullable
  PebbleBlockTag getBlockTag();

  @Nullable
  PebbleCacheTag getCacheTag();

  @Nullable
  PebbleExtendsTag getExtendsTag();

  @Nullable
  PebbleFilterTag getFilterTag();

  @Nullable
  PebbleFlushTag getFlushTag();

  @Nullable
  PebbleForTag getForTag();

  @Nullable
  PebbleIfTag getIfTag();

  @Nullable
  PebbleImportTag getImportTag();

  @Nullable
  PebbleIncludeTag getIncludeTag();

  @Nullable
  PebbleMacroTag getMacroTag();

  @Nullable
  PebbleParallelTag getParallelTag();

  @Nullable
  PebbleSetTag getSetTag();

  @Nullable
  PebbleVerbatimTag getVerbatimTag();

}
