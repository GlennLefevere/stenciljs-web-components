package com.github.glennlefevere.stenciljswebcomponents.dto;

import java.util.List;

public class StencilDocComponent {
    public String tag;
    public List<StencilDocComponentProps> props;
    public List<StencilDocComponentEvents> events;

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

    public List<StencilDocComponentEvents> getEvents() {
        return events;
    }

    public void setEvents(List<StencilDocComponentEvents> events) {
        this.events = events;
    }
}
