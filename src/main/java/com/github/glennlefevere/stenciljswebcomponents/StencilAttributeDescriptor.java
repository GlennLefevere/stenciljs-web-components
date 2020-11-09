package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.completationProvider.IconUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.meta.PsiPresentableMetaData;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.impl.BasicXmlAttributeDescriptor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.net.URL;

public class StencilAttributeDescriptor extends BasicXmlAttributeDescriptor implements XmlAttributeDescriptor, PsiPresentableMetaData {

    private final String name;
    private final boolean required;
    private final XmlTag tag;

    public StencilAttributeDescriptor(String name, XmlTag tag, boolean required) {
        this.name = name;
        this.required = required;
        this.tag = tag;
    }

    public StencilAttributeDescriptor(String name, XmlTag tag) {
        this.name = name;
        this.required = false;
        this.tag = tag;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public boolean hasIdType() {
        return false;
    }

    @Override
    public boolean hasIdRefType() {
        return false;
    }

    @Override
    public @Nullable String getDefaultValue() {
        return null;
    }

    @Override
    public boolean isEnumerated() {
        return false;
    }

    @Override
    public @Nullable String[] getEnumeratedValues() {
        return new String[0];
    }

    @Override
    public @Nullable String validateValue(XmlElement context, String value) {
        return null;
    }

    @Override
    public PsiElement getDeclaration() {
        PsiElement element = tag.getAttribute(getName());
        if (element != null) {
            return element.getOriginalElement();
        }
        return null;
    }

    @Override
    public String getName(PsiElement context) {
        return getName();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void init(PsiElement element) {

    }

    @Override
    public @Nullable @Nls String getTypeName() {
        return null;
    }

    @Override
    public @Nullable Icon getIcon() {
        URL url = IconUtil.class.getClassLoader().getResource("preview_icon.png");
        if (url != null) {
            return new ImageIcon(url);
        }
        return null;
    }
}
