package com.github.glennlefevere.stenciljswebcomponents.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StencilDocComponentProps {
    public String name;
    public boolean required;
    @SerializedName("default")
    public String defaultValue;
    public List<StencilDocComponentPropsValues> values;
}
