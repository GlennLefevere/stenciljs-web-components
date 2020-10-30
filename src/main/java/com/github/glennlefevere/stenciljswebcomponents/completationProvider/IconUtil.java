package com.github.glennlefevere.stenciljswebcomponents.completationProvider;

import com.intellij.codeInsight.lookup.LookupElementBuilder;

import javax.swing.*;

public class IconUtil {

    public static LookupElementBuilder addIcon(LookupElementBuilder lookupElement) {
        ImageIcon icon = new ImageIcon(IconUtil.class.getClassLoader().getResource("preview_icon.png").getFile());
        return lookupElement.withIcon(icon);
    }
}
