package com.github.glennlefevere.stenciljswebcomponents.completationProvider;

import com.github.glennlefevere.stenciljswebcomponents.dto.StencilMergedDoc;
import com.github.glennlefevere.stenciljswebcomponents.listeners.StencilProjectListener;
import com.github.glennlefevere.stenciljswebcomponents.util.CompletionTypeUtil;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class HtmlTagCompletionProvider extends CompletionProvider<CompletionParameters> {
    private static final Logger log = Logger.getInstance(HtmlTagCompletionProvider.class);

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {


        if (CompletionTypeUtil.isTag(parameters)) {
            StencilMergedDoc mergedDoc = StencilProjectListener.INSTANCE.getStencilMergedDocForProject(parameters.getOriginalFile().getProject());
            if (mergedDoc != null && mergedDoc.getComponents() != null) {
                completionResultSet.addAllElements(
                        mergedDoc.getComponents().stream()
                                .map(component -> IconUtil.addIcon(LookupElementBuilder.create(component.tag)))
                                .collect(Collectors.toList()));
            }
        }

    }
}
