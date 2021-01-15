package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.angular.AngularAttributeType;
import com.github.glennlefevere.stenciljswebcomponents.completationProvider.IconUtil;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilDocComponentPropsValues;
import com.intellij.psi.PsiElement;
import com.intellij.psi.meta.PsiPresentableMetaData;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ui.UIUtil;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.impl.BasicXmlAttributeDescriptor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class StencilAttributeDescriptor extends BasicXmlAttributeDescriptor implements XmlAttributeDescriptor, PsiPresentableMetaData {

    private final String name;
    private final AngularAttributeType type;
    private final boolean required;
    private final XmlTag tag;
    private final String defaultValue;
    private final String returnType;
    private boolean isEnumerated = false;
    private String[] enumValues = new String[0];

    public StencilAttributeDescriptor(String name, AngularAttributeType type, XmlTag tag, boolean required, String defaultValue, List<StencilDocComponentPropsValues> values) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.tag = tag;
        this.defaultValue = defaultValue;
        this.returnType = buildType(values);
    }

    public StencilAttributeDescriptor(String name, AngularAttributeType type, XmlTag tag, String eventValue) {
        this.name = name;
        this.type = type;
        this.required = false;
        this.tag = tag;
        this.defaultValue = null;
        this.returnType = buildType(eventValue);
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
        return this.defaultValue;
    }

    @Override
    public boolean isEnumerated() {
        return this.isEnumerated;
    }

    @Override
    public @Nullable String[] getEnumeratedValues() {
        return this.enumValues;
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
        return this.type.buildName(this.name, false);
    }

    @Override
    public void init(PsiElement element) {

    }

    @Override
    public @Nullable @Nls String getTypeName() {
        return this.returnType;
    }

    @Override
    public @Nullable Icon getIcon() {
        String dark = UIUtil.isUnderDarcula() ? "_dark" : "";
        URL url = IconUtil.class.getClassLoader().getResource("preview_icon" + dark + ".png");
        if (url != null) {
            return new ImageIcon(url);
        }
        return null;
    }

    private String buildType(List<StencilDocComponentPropsValues> values) {
        if (values.size() == 1) {
            return values.get(0).type;
        } else if (values.size() > 1) {
            this.isEnumerated = true;
            String[] enumValues = new String[values.size()];
            this.enumValues = values.stream()
                    .map(value -> value.value == null ? transformEnumType(value.type) : value.value)
                    .collect(Collectors.toList())
                    .toArray(enumValues);
            return "string";
        }
        return "";
    }

    public String buildType(String type) {
        return "CustomEvent<" + type + ">";
    }

    private String transformEnumType(String value) {
        return value.substring(value.lastIndexOf(".") + 1);
    }
}
