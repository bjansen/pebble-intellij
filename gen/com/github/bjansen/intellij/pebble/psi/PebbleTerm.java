// This is a generated file. Not intended for manual editing.
package com.github.bjansen.intellij.pebble.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PebbleTerm extends PsiElement {

  @Nullable
  PebbleBooleanLiteral getBooleanLiteral();

  @Nullable
  PebbleIdentifier getIdentifier();

  @Nullable
  PebbleNumericLiteral getNumericLiteral();

  @Nullable
  PebbleStringLiteral getStringLiteral();

}
