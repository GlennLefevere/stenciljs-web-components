package com.github.glennlefevere.stenciljswebcomponents.angular;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum AngularAttributeType {

    REGULAR( "", "", null),
    EVENT("(", ")", "on-"),
    BANANA_BOX_BINDING( "[(", ")]", "bindon-"),
    PROPERTY_BINDING( "[", "]", "bind-");

    private final String myPrefix;
    private final String mySuffix;
    private final String myCanonicalPrefix;

    AngularAttributeType(@NotNull String prefix,
                         @NotNull String suffix,
                         @Nullable String canonicalPrefix) {
        myPrefix = prefix;
        mySuffix = suffix;
        myCanonicalPrefix = canonicalPrefix;
    }

    public String buildName(@NotNull String name, boolean canonical) {
        if (canonical) {
            if (myCanonicalPrefix == null) {
                return null;
            }
            return myCanonicalPrefix + name;
        }
        return myPrefix + name + mySuffix;
    }

}
