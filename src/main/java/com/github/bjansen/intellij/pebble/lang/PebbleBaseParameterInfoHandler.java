package com.github.bjansen.intellij.pebble.lang;

import com.github.bjansen.intellij.pebble.psi.PebbleArgumentList;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.parameterInfo.ParameterInfoContext;
import com.intellij.lang.parameterInfo.ParameterInfoHandlerWithTabActionSupport;
import com.intellij.lang.parameterInfo.ParameterInfoUtils;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

/**
 * Implements all the methods required in 2017.3+ which were removed in 2021.1.
 * By omitting the {@code @Override} annotation, the code compiles and runs both
 * in 2017.3 and 2021.1.
 */
public abstract class PebbleBaseParameterInfoHandler implements
    ParameterInfoHandlerWithTabActionSupport<PebbleArgumentList, PsiElement, PsiElement> {

    public boolean couldShowInLookup() {
        return true;
    }

    @Nullable
    public String getParameterCloseChars() {
        return ParameterInfoUtils.DEFAULT_PARAMETER_CLOSE_CHARS;
    }

    @Nullable
    public Object[] getParametersForDocumentation(PsiElement p, ParameterInfoContext context) {
        return null;
    }

    @Nullable
    public Object[] getParametersForLookup(LookupElement item, ParameterInfoContext context) {
        return null;
    }

    public boolean tracksParameterIndex() {
        return true;
    }
}
