package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.dto.StencilDocComponent;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilMergedDoc;
import com.github.glennlefevere.stenciljswebcomponents.listeners.AngularProjectListener;
import com.github.glennlefevere.stenciljswebcomponents.listeners.StencilProjectListener;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbService;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.glennlefevere.stenciljswebcomponents.angular.AngularAttributeType.*;

public class WebComponentAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {
    private static final Logger log = Logger.getInstance(WebComponentAttributeDescriptorsProvider.class);

    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        if (context == null || DumbService.isDumb(context.getProject())) {
            return new XmlAttributeDescriptor[0];
        }
        StencilMergedDoc mergedDoc = StencilProjectListener.INSTANCE.getStencilMergedDocForProject(context.getProject());
        if (mergedDoc == null || mergedDoc.getComponents() == null) {
            return null;
        }

        List<StencilAttributeDescriptor> descriptorList = mergedDoc.getComponents().stream()
                .filter(comp -> comp.getTag().equals(context.getName()))
                .map(comp -> getAllAttributeDescriptors(comp, context))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        StencilAttributeDescriptor[] descriptors = new StencilAttributeDescriptor[descriptorList.size()];
        descriptorList.toArray(descriptors);

        return descriptors;
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        if (context == null || DumbService.isDumb(context.getProject())) {
            return null;
        }
        StencilMergedDoc mergedDoc = StencilProjectListener.INSTANCE.getStencilMergedDocForProject(context.getProject());
        if (mergedDoc == null || mergedDoc.getComponents() == null) {
            return null;
        }

        return mergedDoc.getComponents().stream()
                .filter(comp -> comp.getTag().equals(context.getName()))
                .map(comp -> getAllAttributeDescriptors(comp, context))
                .flatMap(Collection::stream)
                .filter(descriptor -> descriptor.getName().equals(attributeName))
                .findAny().orElse(null);
    }

    private List<StencilAttributeDescriptor> getAllAttributeDescriptors(StencilDocComponent component, XmlTag tag) {
        List<StencilAttributeDescriptor> descriptors = new ArrayList<>();

        component.getProps().forEach(prop -> {
            descriptors.add(new StencilAttributeDescriptor(prop.name, REGULAR, tag, prop.required, prop.defaultValue, prop.values));
            if (AngularProjectListener.INSTANCE.isAngularProject(tag.getProject())) {
                descriptors.add(new StencilAttributeDescriptor(prop.name, BANANA_BOX_BINDING, tag, prop.required, prop.defaultValue, prop.values));
                descriptors.add(new StencilAttributeDescriptor(prop.name, PROPERTY_BINDING, tag, prop.required, prop.defaultValue, prop.values));
            }
        });

        component.getEvents().forEach(event -> {
            descriptors.add(new StencilAttributeDescriptor(event.event, REGULAR, tag, event.detail));
            if (AngularProjectListener.INSTANCE.isAngularProject(tag.getProject())) {
                descriptors.add(new StencilAttributeDescriptor(event.event, EVENT, tag, event.detail));
            }
        });

        return descriptors;
    }

}
