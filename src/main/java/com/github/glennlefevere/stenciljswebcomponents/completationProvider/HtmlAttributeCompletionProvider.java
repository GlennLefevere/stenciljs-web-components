package com.github.glennlefevere.stenciljswebcomponents.completationProvider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class HtmlAttributeCompletionProvider extends CompletionProvider<CompletionParameters> {
    private static final Logger log = Logger.getInstance(HtmlAttributeCompletionProvider.class);

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {

/*        StencilMergedDoc stencilDoc = StencilDocReader.INSTANCE.getStencilDoc();
        PsiReference reference = parameters.getPosition().getContainingFile().findReferenceAt(parameters.getOffset());
        List<LookupElementBuilder> elements = new ArrayList<>();
        if (reference != null) {
            String name = ((XmlAttributeReference) reference).getElement().getParent().getName();
            elements = stencilDoc.getComponents().stream()
                    .filter(stencilDocComponent -> stencilDocComponent.tag.equals(name))
                    .flatMap(stencilDocComponent -> stencilDocComponent.getProps().stream())
                    .map(prop -> IconUtil.addIcon(LookupElementBuilder.create(prop.name)))
                    .collect(Collectors.toList());

        } else {
            if (parameters.getPosition().getParent() instanceof XmlAttributeValue) {
                XmlAttributeImpl xmlAttribute = (XmlAttributeImpl) parameters.getPosition().getParent().getParent();
                HtmlTagImpl htmlTag = (HtmlTagImpl) xmlAttribute.getParent();
                elements = stencilDoc.getComponents().stream()
                        .filter(stencilDocComponent -> stencilDocComponent.tag.equals(htmlTag.getName()))
                        .flatMap(stencilDocComponent -> stencilDocComponent.getProps().stream())
                        .filter(prop -> prop.name.equals(xmlAttribute.getName()))
                        .flatMap(prop -> prop.values.stream())
                        .filter(Objects::nonNull)
                        .map(value -> IconUtil.addIcon(LookupElementBuilder.create(value.value)))
                        .collect(Collectors.toList());
            }
        }

        completionResultSet.addAllElements(elements);*/
    }

}
