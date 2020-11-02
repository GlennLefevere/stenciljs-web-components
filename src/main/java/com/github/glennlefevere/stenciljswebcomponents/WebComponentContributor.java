package com.github.glennlefevere.stenciljswebcomponents;


import static com.intellij.patterns.PlatformPatterns.psiElement;

import com.github.glennlefevere.stenciljswebcomponents.completationProvider.HtmlAttributeCompletionProvider;
import com.github.glennlefevere.stenciljswebcomponents.completationProvider.HtmlTagCompletionProvider;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.patterns.XmlPatterns;

public class WebComponentContributor extends CompletionContributor {
    private static final Logger log = Logger.getInstance(WebComponentContributor.class);

    public WebComponentContributor() {
        log.info("Initialized");

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
