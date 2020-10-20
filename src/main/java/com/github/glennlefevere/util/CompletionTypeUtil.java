package com.github.glennlefevere.util;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.psi.PsiElement;

public class CompletionTypeUtil {

    public static boolean isTag(CompletionParameters parameters) {
        PsiElement position = parameters.getPosition().getPrevSibling();
        if (position != null) {
            return position.getText().equals("<");
        }
        return false;
    }
}
