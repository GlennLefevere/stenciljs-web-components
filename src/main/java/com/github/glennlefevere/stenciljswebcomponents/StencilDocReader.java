package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.dto.StencilDoc;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilMergedDoc;
import com.github.glennlefevere.stenciljswebcomponents.util.ModulePathUtil;
import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class StencilDocReader {
    private static final Logger log = Logger.getInstance(StencilDocReader.class);

    public static StencilDocReader INSTANCE = new StencilDocReader();

    private StencilMergedDoc stencilDoc;

    private Set<Path> dependencies = new HashSet<>();

    private StencilDocReader() {
        Optional<StencilMergedDoc> doc = this.deserialize();
        doc.ifPresent(value -> this.stencilDoc = value);
    }

    private Optional<StencilMergedDoc> deserialize() {
        StencilMergedDoc mergedDoc = new StencilMergedDoc();
        for (Project project : Arrays.stream(ProjectManager.getInstance().getOpenProjects()).filter(project -> project.getBasePath() != null).collect(Collectors.toList())) {
            try {
                for (Path docPath : getAllStencilDocs(project.getBasePath())) {
                    String json = String.join("", Files.readAllLines(docPath));
                    StencilDoc stencilDoc = new Gson().fromJson(json, StencilDoc.class);
                    mergedDoc.addComponents(stencilDoc.components);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.of(mergedDoc);
    }

    private List<Path> getAllStencilDocs(String projectBasePath) throws IOException {
        Set<Path> allStencilModules = getAllModulesUsingStencil(projectBasePath);
        List<Path> allStencilDocs = new ArrayList<>();
        dependencies.addAll(allStencilModules);

        for (Path stencilModule : allStencilModules) {
            allStencilDocs.addAll(Files.walk(stencilModule.getParent())
                    .filter(ModulePathUtil::isJsonFile)
                    .filter(ModulePathUtil::isStencilDocsFile)
                    .collect(Collectors.toList())
            );
        }

        return allStencilDocs;
    }

    private Set<Path> getAllModulesUsingStencil(String projectBasePath) throws IOException {
        Path path = Paths.get(projectBasePath);
        return Files.walk(path)
                .filter(p -> !dependencies.contains(p))
                .filter(Files::isRegularFile)
                .filter(ModulePathUtil::isPackageJsonOfModule)
                .filter(ModulePathUtil::isStencilModule)
                .collect(Collectors.toSet());
    }

    public StencilMergedDoc getStencilDoc() {
        if (stencilDoc == null) {
            Optional<StencilMergedDoc> doc = this.deserialize();
            doc.ifPresent(value -> this.stencilDoc = value);
        }
        return stencilDoc;
    }
}
