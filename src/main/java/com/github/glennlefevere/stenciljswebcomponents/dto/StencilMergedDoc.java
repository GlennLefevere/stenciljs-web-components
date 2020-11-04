package com.github.glennlefevere.stenciljswebcomponents.dto;

import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.List;

public class StencilMergedDoc {

    private static final Logger log = Logger.getInstance(StencilMergedDoc.class);
    private List<StencilDocComponent> components = new ArrayList<>();

    public List<StencilDocComponent> getComponents() {
        return components;
    }

    public void addComponents(List<StencilDocComponent> components) {
        if (this.components == null) {
            this.components = new ArrayList<>();
        }
        this.components.addAll(components);
    }
}
