package com.github.glennlefevere.stenciljswebcomponents;

import com.github.glennlefevere.stenciljswebcomponents.listeners.AngularProjectListener;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.impl.source.html.HtmlLikeFile;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;

public class StencilAngularUndefinedBindingInspection extends LocalInspectionTool {
    private static final Logger log = Logger.getInstance(StencilAngularUndefinedBindingInspection.class);

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly, @NotNull LocalInspectionToolSession session) {
        if (holder.getFile() instanceof HtmlLikeFile
                && AngularProjectListener.INSTANCE.isAngularProject(holder.getProject())) {
            return doBuildVisitor(holder);
        }
        return PsiElementVisitor.EMPTY_VISITOR;
    }

    private @NotNull PsiElementVisitor doBuildVisitor(@NotNull ProblemsHolder holder) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                log.error("test");
                holder.getResults().forEach(r -> log.error(r.getDescriptionTemplate()));
                /*holder.getResults().stream().filter(r -> r.getPsiElement().equals(attribute.getNameElement()))
                        .forEach(r -> log.error(r.getDescriptionTemplate()));*/
            }
        };
    }

}
