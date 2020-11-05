package com.github.glennlefevere.stenciljswebcomponents.preloading;

import com.github.glennlefevere.stenciljswebcomponents.listeners.AngularProjectListener;
import com.github.glennlefevere.stenciljswebcomponents.listeners.StencilProjectListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class StencilPreloadingActivity extends PreloadingActivity {

    @Override
    public void preload(@NotNull ProgressIndicator indicator) {
        final MessageBusConnection connect = ApplicationManager.getApplication().getMessageBus().connect();
        connect.subscribe(ProjectManager.TOPIC, StencilProjectListener.INSTANCE);
        connect.subscribe(ProjectManager.TOPIC, AngularProjectListener.INSTANCE);

        for(Project project : ProjectManager.getInstance().getOpenProjects()) {
            StencilProjectListener.INSTANCE.projectOpened(project);
            AngularProjectListener.INSTANCE.projectOpened(project);
        }

    }

}
