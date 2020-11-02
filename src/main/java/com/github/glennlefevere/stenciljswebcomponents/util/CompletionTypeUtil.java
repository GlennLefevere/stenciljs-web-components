package com.github.glennlefevere.stenciljswebcomponents.util;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;

public class CompletionTypeUtil {
    private static final Logger log = Logger.getInstance(CompletionTypeUtil.class);

    public static boolean isTag(CompletionParameters parameters) {
        PsiElement position = parameters.getPosition().getPrevSibling();
        if (position != null) {
            return position.getText().equals("<");
        }
        return false;
    }
}
