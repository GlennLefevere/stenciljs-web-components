package com.github.glennlefevere.stenciljswebcomponents.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModulePathUtil {

    public static boolean isPackageJsonOfModule(Path file) {
        return (file.toAbsolutePath().toString().toLowerCase().contains("node_modules") || file.toAbsolutePath().toString().toLowerCase().contains("dist")) &&
               file.toAbsolutePath().toString().toLowerCase().endsWith("package.json");
    }

    public static boolean isJsonFile(Path file) {
        return file.toAbsolutePath().toString().toLowerCase().endsWith(".json");
    }

    public static boolean isStencilModule(Path file) {
        try {
            String fileContents = String.join("", Files.readAllLines(file));
            return fileContents.contains("@stencil/core");
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isStencilDocsFile(Path file) {
        try {
            String fileContents = String.join("", Files.readAllLines(file));
            return fileContents.contains("@stencil/core") &&
                   fileContents.contains("\"components\"");
        } catch (IOException e) {
            return false;
        }
    }

}
