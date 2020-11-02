package com.github.glennlefevere.stenciljswebcomponents.dto;

import java.util.ArrayList;
import java.util.List;

public class StencilMergedDoc {
    private List<StencilDocComponent> components;

    public List<StencilDocComponent> getComponents() {
        return components;
    }

    public void addComponents(List<StencilDocComponent> components) {
        if(this.components == null) {
            this.components = new ArrayList<>();
        }
        this.components.addAll(components);
    }
}
