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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StencilDocReader {
    private static final Logger log = Logger.getInstance(StencilDocReader.class);

    public static StencilDocReader INSTANCE = new StencilDocReader();

    public StencilMergedDoc stencilDoc;

    private StencilDocReader() {
        Optional<StencilMergedDoc> doc = this.deserialize();
        doc.ifPresent(value -> this.stencilDoc = value);
    }

    private Optional<StencilMergedDoc> deserialize() {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        if (project != null && project.getBasePath() != null) {
            try {
                StencilMergedDoc mergedDoc = new StencilMergedDoc();

                for (Path docPath : getAllStencilDocs(project.getBasePath())) {
                    String json = String.join("", Files.readAllLines(docPath));
                    StencilDoc stencilDoc = new Gson().fromJson(json, StencilDoc.class);
                    mergedDoc.addComponents(stencilDoc.components);
                }

                return Optional.of(mergedDoc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.empty();
    }

    private List<Path> getAllStencilDocs(String projectBasePath) throws IOException {
        List<Path> allStencilModules = getAllModulesUsingStencil(projectBasePath);
        List<Path> allStencilDocs = new ArrayList<>();

        for (Path stencilModule : allStencilModules) {
            log.info(stencilModule.toString());
            allStencilDocs.addAll(Files.walk(stencilModule.getParent())
                                       .filter(ModulePathUtil::isJsonFile)
                                       .filter(ModulePathUtil::isStencilDocsFile)
                                       .collect(Collectors.toList())
            );
        }

        return allStencilDocs;
    }

    private List<Path> getAllModulesUsingStencil(String projectBasePath) throws IOException {
        Path path = Paths.get(projectBasePath);
        return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .filter(ModulePathUtil::isPackageJsonOfModule)
                    .filter(ModulePathUtil::isStencilModule)
                    .collect(Collectors.toList());
    }

}
