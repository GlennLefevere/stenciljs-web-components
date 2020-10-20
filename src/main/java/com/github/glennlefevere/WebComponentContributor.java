package com.github.glennlefevere;


import static com.intellij.patterns.PlatformPatterns.psiElement;

import com.github.glennlefevere.completationProvider.HtmlAttributeCompletionProvider;
import com.github.glennlefevere.completationProvider.HtmlTagCompletionProvider;
import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.XmlPatterns;

public class WebComponentContributor extends CompletionContributor {

    public WebComponentContributor() {

        extend(CompletionType.BASIC,
               psiElement().inside(XmlPatterns.xmlTag()),
               new HtmlTagCompletionProvider()
        );

        extend(CompletionType.BASIC,
               psiElement().inside(XmlPatterns.xmlAttribute()),
               new HtmlAttributeCompletionProvider()
        );

    }

}
