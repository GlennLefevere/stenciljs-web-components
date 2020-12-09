package com.github.glennlefevere.stenciljswebcomponents.listeners;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FileListener implements BulkFileListener {
    private static final Logger log = Logger.getInstance(FileListener.class);

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        events.forEach(event -> {
            if (StencilProjectListener.INSTANCE.isStencilDocFileFromProject(event.getPath())) {
                Project project = StencilProjectListener.INSTANCE.getProjectFromFilePath(event.getPath());
                StencilProjectListener.INSTANCE.projectFileUpdated(project);
            }
        });
    }
}
