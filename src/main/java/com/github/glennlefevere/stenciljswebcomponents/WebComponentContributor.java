package com.github.glennlefevere.stenciljswebcomponents;


import com.github.glennlefevere.stenciljswebcomponents.completationProvider.HtmlTagCompletionProvider;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.patterns.XmlPatterns;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class WebComponentContributor extends CompletionContributor {
    private static final Logger log = Logger.getInstance(WebComponentContributor.class);

    public WebComponentContributor() {

        extend(CompletionType.BASIC,
               psiElement().inside(XmlPatterns.xmlTag()),
               new HtmlTagCompletionProvider()
        );

    }

}
