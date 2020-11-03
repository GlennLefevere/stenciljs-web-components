package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.dto.StencilDocComponentProps;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StencilAttributeDescriptor implements XmlAttributeDescriptor {

    private final StencilDocComponentProps props;
    private final XmlTag tag;

    public StencilAttributeDescriptor(StencilDocComponentProps props, XmlTag tag) {
        this.props = props;
        this.tag = tag;
    }

    @Override
    public boolean isRequired() {
        return props.required;
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
        return Objects.requireNonNull(tag.getAttribute(getName())).getOriginalElement();
    }

    @Override
    public String getName(PsiElement context) {
        return getName();
    }

    @Override
    public String getName() {
        return props.name;
    }

    @Override
    public void init(PsiElement element) {

    }
}
