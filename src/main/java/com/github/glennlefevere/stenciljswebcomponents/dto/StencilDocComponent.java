package com.github.glennlefevere.stenciljswebcomponents.dto;

import java.util.List;

public class StencilDocComponent {
    public String tag;
    public List<StencilDocComponentProps> props;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<StencilDocComponentProps> getProps() {
        return props;
    }

    public void setProps(List<StencilDocComponentProps> props) {
        this.props = props;
    }
}
