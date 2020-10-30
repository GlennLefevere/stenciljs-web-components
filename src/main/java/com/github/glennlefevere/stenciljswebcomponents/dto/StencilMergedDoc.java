package com.github.glennlefevere.stenciljswebcomponents.dto;

import java.util.ArrayList;
import java.util.List;

public class StencilMergedDoc {
    private List<StencilDocComponent> components = new ArrayList<>();

    public List<StencilDocComponent> getComponents() {
        return components;
    }

    public void addComponents(List<StencilDocComponent> components) {
        this.components.addAll(components);
    }
}
