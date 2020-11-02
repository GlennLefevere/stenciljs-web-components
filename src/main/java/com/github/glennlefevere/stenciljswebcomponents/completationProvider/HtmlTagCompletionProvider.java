package com.github.glennlefevere.stenciljswebcomponents.completationProvider;

import com.github.glennlefevere.stenciljswebcomponents.StencilDocReader;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilMergedDoc;
import com.github.glennlefevere.stenciljswebcomponents.util.CompletionTypeUtil;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class HtmlTagCompletionProvider extends CompletionProvider<CompletionParameters> {
    private static final Logger log = Logger.getInstance(HtmlTagCompletionProvider.class);

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {


        if (CompletionTypeUtil.isTag(parameters)) {
            StencilMergedDoc stencilDoc = StencilDocReader.INSTANCE.stencilDoc;
            if(stencilDoc.getComponents() != null) {
                stencilDoc.getComponents()
                          .forEach(stencilDocComponent -> {
                              LookupElementBuilder lookupElement = LookupElementBuilder.create(stencilDocComponent.tag);
                              completionResultSet.addElement(IconUtil.addIcon(lookupElement));
                          });
            }
        }

    }
}
