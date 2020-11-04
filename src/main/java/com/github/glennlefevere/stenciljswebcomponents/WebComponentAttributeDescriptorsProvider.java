package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.angular.AngularApplication;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilDocComponent;
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

    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        if(context == null || DumbService.isDumb(context.getProject())){
            return new XmlAttributeDescriptor[0];
        }
        List<StencilAttributeDescriptor> descriptorList = StencilDocReader.INSTANCE.getStencilDoc().getComponents().stream()
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
        if(context == null || DumbService.isDumb(context.getProject()) || StencilDocReader.INSTANCE.getStencilDoc() == null){
            return null;
        }
       return StencilDocReader.INSTANCE.getStencilDoc().getComponents().stream()
                .filter(comp -> comp.getTag().equals(context.getName()))
                .map(comp -> getAllAttributeDescriptors(comp, context))
                .flatMap(Collection::stream)
                .filter(descriptor -> descriptor.getName().equals(attributeName))
                .findAny().orElse(null);
    }

    private List<StencilAttributeDescriptor> getAllAttributeDescriptors(StencilDocComponent component, XmlTag tag) {
        List<StencilAttributeDescriptor> descriptors = new ArrayList<>();

        component.getProps().forEach(prop -> {
            descriptors.add(new StencilAttributeDescriptor(prop.name, tag, prop.required));
            if(AngularApplication.INSTANCE.isAngularApplication) {
                descriptors.add(new StencilAttributeDescriptor(BANANA_BOX_BINDING.buildName(prop.name, false), tag, prop.required));
                descriptors.add(new StencilAttributeDescriptor(PROPERTY_BINDING.buildName(prop.name, false), tag, prop.required));
            }
        });

        component.getEvents().forEach(event -> {
            descriptors.add(new StencilAttributeDescriptor(event.event, tag));
            if(AngularApplication.INSTANCE.isAngularApplication) {
                descriptors.add(new StencilAttributeDescriptor(EVENT.buildName(event.event, false), tag));
            }
        });

        return descriptors;
    }

}
