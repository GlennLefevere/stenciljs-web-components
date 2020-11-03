package com.github.glennlefevere.stenciljswebcomponents;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.html.dtd.HtmlElementDescriptorImpl;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlElementsGroup;
import com.intellij.xml.XmlNSDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StencilStandardTagDescriptor extends HtmlElementDescriptorImpl {

    private final HtmlElementDescriptorImpl myDelegate;

    public StencilStandardTagDescriptor(@NotNull HtmlElementDescriptorImpl delegate) {
        super(delegate, true, false);
        myDelegate = delegate;
    }

    @Override
    public String getQualifiedName() {
        return myDelegate.getQualifiedName();
    }

    @Override
    public String getDefaultName() {
        return myDelegate.getDefaultName();
    }

    @Override
    public XmlElementDescriptor[] getElementsDescriptors(XmlTag context) {
        return myDelegate.getElementsDescriptors(context);
    }

    @Override
    public @Nullable XmlElementDescriptor getElementDescriptor(XmlTag childTag, XmlTag contextTag) {
        return myDelegate.getElementDescriptor(childTag, contextTag);
    }

    @Override
    public XmlAttributeDescriptor[] getAttributesDescriptors(@Nullable XmlTag context) {
        return myDelegate.getAttributesDescriptors(context);
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(String attributeName, @Nullable XmlTag context) {
        return mergeWithStencilDescriptorIfPossible(myDelegate.getAttributeDescriptor(attributeName, context), attributeName, context);
    }

    @Override
    public @Nullable XmlNSDescriptor getNSDescriptor() {
        return myDelegate.getNSDescriptor();
    }

    @Override
    public @Nullable XmlElementsGroup getTopGroup() {
        return myDelegate.getTopGroup();
    }

    @Override
    public int getContentType() {
        return myDelegate.getContentType();
    }

    @Override
    public @Nullable String getDefaultValue() {
        return myDelegate.getDefaultValue();
    }

    @Override
    public PsiElement getDeclaration() {
        return myDelegate.getDeclaration();
    }

    @Override
    public String getName(PsiElement context) {
        return myDelegate.getName(context);
    }

    @Override
    public String getName() {
        return myDelegate.getName();
    }

    @Override
    public void init(PsiElement element) {
        myDelegate.init(element);
    }

    @Override
    public XmlAttributeDescriptor[] getDefaultAttributeDescriptors(XmlTag context) {
        return myDelegate.getDefaultAttributeDescriptors(context);
    }

    @Override
    public boolean allowElementsFromNamespace(String namespace, XmlTag context) {
        return myDelegate.allowElementsFromNamespace(namespace, context);
    }
    static XmlAttributeDescriptor mergeWithStencilDescriptorIfPossible(@Nullable XmlAttributeDescriptor descriptor,
                                                                       String attributeName,
                                                                       @Nullable XmlTag context) {
/*        if (!(descriptor instanceof Angular2AttributeDescriptor)) {
            Angular2AttributeDescriptor angularDescriptor = tryCast(
                    RelaxedHtmlFromSchemaElementDescriptor.getAttributeDescriptorFromFacelets(attributeName, context),
                    Angular2AttributeDescriptor.class);
            if (angularDescriptor != null) {
                return angularDescriptor.merge(descriptor);
            }
        }*/
        return descriptor;
    }

}
