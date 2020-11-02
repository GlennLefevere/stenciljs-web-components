package com.github.glennlefevere.stenciljswebcomponents.completationProvider;

import com.github.glennlefevere.stenciljswebcomponents.StencilDocReader;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilMergedDoc;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.html.HtmlTagImpl;
import com.intellij.psi.impl.source.xml.XmlAttributeImpl;
import com.intellij.psi.impl.source.xml.XmlAttributeReference;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HtmlAttributeCompletionProvider extends CompletionProvider<CompletionParameters> {
    private static final Logger log = Logger.getInstance(HtmlAttributeCompletionProvider.class);

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {

        StencilMergedDoc stencilDoc = StencilDocReader.INSTANCE.stencilDoc;
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

        completionResultSet.addAllElements(elements);
    }

}
