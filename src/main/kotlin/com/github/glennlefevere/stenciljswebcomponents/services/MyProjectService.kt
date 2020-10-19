package com.github.glennlefevere.stenciljswebcomponents.services

import com.intellij.openapi.project.Project
import com.github.glennlefevere.stenciljswebcomponents.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
