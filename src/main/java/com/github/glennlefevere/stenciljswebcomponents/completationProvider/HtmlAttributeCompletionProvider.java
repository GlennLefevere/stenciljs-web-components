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

public class HtmlAttributeCompletionProvider extends CompletionProvider<CompletionParameters> {
    private static final Logger log = Logger.getInstance(HtmlAttributeCompletionProvider.class);

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {

        StencilMergedDoc stencilDoc = StencilDocReader.INSTANCE.stencilDoc;
        PsiReference reference = parameters.getPosition().getContainingFile().findReferenceAt(parameters.getOffset());
        if (reference != null) {
            String name = ((XmlAttributeReference) reference).getElement().getParent().getName();
            stencilDoc.getComponents().stream()
                      .filter(stencilDocComponent -> stencilDocComponent.tag.equals(name))
                      .forEach(stencilDocComponent -> {
                          stencilDocComponent.props
                                  .forEach(stencilDocComponentProp -> {
                                      LookupElementBuilder lookupElement = LookupElementBuilder.create(stencilDocComponentProp.name);
                                      completionResultSet.addElement(IconUtil.addIcon(lookupElement));
                                  });
                      });
        } else {
            if (parameters.getPosition().getParent() instanceof XmlAttributeValue) {
                XmlAttributeImpl xmlAttribute = (XmlAttributeImpl) parameters.getPosition().getParent().getParent();
                HtmlTagImpl htmlTag = (HtmlTagImpl) xmlAttribute.getParent();
                stencilDoc.getComponents().stream()
                          .filter(stencilDocComponent -> stencilDocComponent.tag.equals(htmlTag.getName()))
                          .forEach(stencilDocComponent -> {
                              stencilDocComponent.props.stream()
                                                       .filter(stencilDocComponentProp -> stencilDocComponentProp.name.equals(xmlAttribute.getName()))
                                                       .forEach(stencilDocComponentProp -> {
                                                           stencilDocComponentProp.values.stream()
                                                                                         .filter(stencilDocComponentPropsValues -> stencilDocComponentPropsValues.value != null)
                                                                                         .forEach(stencilDocComponentPropsValues -> {
                                                                                             LookupElementBuilder lookupElement = LookupElementBuilder.create(stencilDocComponentPropsValues.value);
                                                                                             completionResultSet.addElement(IconUtil.addIcon(lookupElement));
                                                                                         });
                                                       });
                          });
            }
        }

    }

}
