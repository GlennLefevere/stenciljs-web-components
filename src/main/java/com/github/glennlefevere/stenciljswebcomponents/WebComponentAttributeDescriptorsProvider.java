package com.github.glennlefevere.stenciljswebcomponents;

import com.intellij.openapi.project.DumbService;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class WebComponentAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {
    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        if(context == null || DumbService.isDumb(context.getProject())){
            return new XmlAttributeDescriptor[0];
        }
        List<StencilAttributeDescriptor> descriptorList = StencilDocReader.INSTANCE.stencilDoc.getComponents().stream()
                .filter(comp -> comp.getTag().equals(context.getName()))
                .flatMap(comp -> comp.getProps().stream())
                .map(props -> new StencilAttributeDescriptor(props, context))
                .collect(Collectors.toList());

        StencilAttributeDescriptor[] descriptors = new StencilAttributeDescriptor[descriptorList.size()];
        descriptorList.toArray(descriptors);

        return descriptors;
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        if(context == null || DumbService.isDumb(context.getProject())){
            return null;
        }
        return StencilDocReader.INSTANCE.stencilDoc.getComponents().stream()
                .filter(comp -> comp.getTag().equals(context.getName()))
                .flatMap(comp -> comp.getProps().stream())
                .filter(prop -> prop.name.equals(attributeName))
                .findAny()
                .map(prop -> new StencilAttributeDescriptor(prop, context)).orElse(null);
    }
}
