package com.github.glennlefevere.stenciljswebcomponents;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.html.dtd.HtmlNSDescriptorImpl;
import com.intellij.psi.impl.source.xml.XmlDescriptorUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlElementsGroup;
import com.intellij.xml.XmlNSDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StencilTagDescriptor implements XmlElementDescriptor {
    private final String myName;
    private final PsiElement declaration;

    public StencilTagDescriptor(@NotNull XmlTag tag) {
        myName = tag.getLocalName();
        declaration = tag.getOriginalElement();
    }


    @Override
    public String getQualifiedName() {
        return myName;
    }

    @Override
    public String getDefaultName() {
        return myName;
    }

    @Override
    public XmlElementDescriptor[] getElementsDescriptors(XmlTag context) {
        return XmlDescriptorUtil.getElementsDescriptors(context);
    }

    @Override
    public @Nullable XmlElementDescriptor getElementDescriptor(XmlTag childTag, XmlTag contextTag) {
        return XmlDescriptorUtil.getElementDescriptor(childTag, contextTag);
    }

    @Override
    public XmlAttributeDescriptor[] getAttributesDescriptors(@Nullable XmlTag context) {
        return HtmlNSDescriptorImpl.getCommonAttributeDescriptors(context);
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(String attributeName, @Nullable XmlTag context) {
        return HtmlNSDescriptorImpl.getCommonAttributeDescriptor(attributeName, context);
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(XmlAttribute attribute) {
        return getAttributeDescriptor(attribute.getName(), attribute.getParent());
    }

    @Override
    public @Nullable XmlNSDescriptor getNSDescriptor() {
        return null;
    }

    @Override
    public @Nullable XmlElementsGroup getTopGroup() {
        return null;
    }

    @Override
    public int getContentType() {
        return CONTENT_TYPE_ANY;
    }

    @Override
    public @Nullable String getDefaultValue() {
        return null;
    }

    @Override
    public PsiElement getDeclaration() {
        return declaration;
    }

    @Override
    public String getName(PsiElement context) {
        return getName();
    }

    @Override
    public String getName() {
        return myName;
    }

    @Override
    public void init(PsiElement element) {

    }

}
