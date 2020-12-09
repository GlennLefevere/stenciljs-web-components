package com.github.glennlefevere.stenciljswebcomponents.listeners;

import com.github.glennlefevere.stenciljswebcomponents.dto.StencilDoc;
import com.github.glennlefevere.stenciljswebcomponents.dto.StencilMergedDoc;
import com.github.glennlefevere.stenciljswebcomponents.util.ModulePathUtil;
import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class StencilProjectListener implements ProjectManagerListener {
    private static final Logger log = Logger.getInstance(StencilProjectListener.class);

    public static final StencilProjectListener INSTANCE = new StencilProjectListener();

    private final Map<Project, StencilMergedDoc> projectStencilDocMap = new HashMap<>();
    private final Map<Project, List<Path>> projectStencilDocPathMap = new HashMap<>();

    @Override
    public void projectOpened(@NotNull Project project) {
        try {
            List<Path> paths = getAllStencilDocs(project.getBasePath());
            StencilMergedDoc mergedDoc = getMergedDocForFilePaths(paths);
            this.projectStencilDocPathMap.put(project, paths);
            this.projectStencilDocMap.put(project, mergedDoc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void projectClosed(@NotNull Project project) {
        this.projectStencilDocMap.remove(project);
        this.projectStencilDocPathMap.remove(project);
    }

    public StencilMergedDoc getStencilMergedDocForProject(Project project) {
        return this.projectStencilDocMap.get(project);
    }

    public boolean isStencilDocFileFromProject(String path) {
        for (Project project : projectStencilDocPathMap.keySet()) {
            return projectStencilDocPathMap.get(project).stream().anyMatch(filePath -> filePath.toString().replaceAll("\\\\", "/").equals(path));
        }
        return false;
    }

    public Project getProjectFromFilePath(String path) {
        for (Project project : projectStencilDocPathMap.keySet()) {
            if (projectStencilDocPathMap.get(project).stream().anyMatch(filePath -> filePath.toString().replaceAll("\\\\", "/").equals(path))) {
                return project;
            }
        }
        return null;
    }

    public void projectFileUpdated(Project project) {
        try {
            StencilMergedDoc mergedDoc = getMergedDocForFilePaths(this.projectStencilDocPathMap.get(project));
            projectStencilDocMap.replace(project, mergedDoc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private StencilMergedDoc getMergedDocForFilePaths(List<Path> paths) throws IOException {
        StencilMergedDoc mergedDoc = new StencilMergedDoc();

        for (Path docPath : paths) {
            String json = String.join("", Files.readAllLines(docPath));
            StencilDoc stencilDoc = new Gson().fromJson(json, StencilDoc.class);
            if (stencilDoc != null && stencilDoc.components != null) {
                mergedDoc.addComponents(stencilDoc.components);
            }
        }

        return mergedDoc;
    }

    private List<Path> getAllStencilDocs(String projectBasePath) throws IOException {
        Set<Path> allStencilModules = getAllModulesUsingStencil(projectBasePath);
        List<Path> allStencilDocs = new ArrayList<>();

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
                .filter(Files::isRegularFile)
                .filter(ModulePathUtil::isPackageJsonOfModule)
                .filter(ModulePathUtil::isStencilModule)
                .collect(Collectors.toSet());
    }

}
