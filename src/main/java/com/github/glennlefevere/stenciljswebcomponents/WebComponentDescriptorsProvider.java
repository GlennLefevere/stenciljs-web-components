package com.github.glennlefevere.stenciljswebcomponents;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.html.HtmlTag;
import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlNSDescriptor;
import com.intellij.xml.impl.schema.AnyXmlElementDescriptor;
import org.jetbrains.annotations.Nullable;

public class WebComponentDescriptorsProvider implements XmlElementDescriptorProvider {
    private static final Logger log = Logger.getInstance(WebComponentDescriptorsProvider.class);

    @Override
    public @Nullable XmlElementDescriptor getDescriptor(XmlTag tag) {
        if (!(tag instanceof HtmlTag)) return null;

        final XmlNSDescriptor nsDescriptor = tag.getNSDescriptor(tag.getNamespace(), false);
        final XmlElementDescriptor descriptor = nsDescriptor != null ? nsDescriptor.getElementDescriptor(tag) : null;
        if (descriptor != null && !(descriptor instanceof AnyXmlElementDescriptor)) {
            return null;
        }

        if(StencilDocReader.INSTANCE.getStencilDoc() == null || StencilDocReader.INSTANCE.getStencilDoc().getComponents().stream().noneMatch(comp -> comp.getTag().equals(tag.getName()))) {
           return null;
        }
        return new StencilTagDescriptor(tag);
    }

/*    private static XmlElementDescriptor getWrappedDescriptorFromNamespace(@NotNull XmlTag xmlTag) {
        XmlElementDescriptor elementDescriptor = null;
        final XmlNSDescriptor nsDescriptor = xmlTag.getNSDescriptor(xmlTag.getNamespace(), false);

        if (nsDescriptor != null) {
            if (!DumbService.getInstance(xmlTag.getProject()).isDumb() || DumbService.isDumbAware(nsDescriptor)) {
                elementDescriptor = nsDescriptor.getElementDescriptor(xmlTag);
            }
        }
        if (elementDescriptor instanceof HtmlElementDescriptorImpl) {
            return new StencilStandardTagDescriptor((HtmlElementDescriptorImpl)elementDescriptor);
        }
        return null;
    }*/
}
