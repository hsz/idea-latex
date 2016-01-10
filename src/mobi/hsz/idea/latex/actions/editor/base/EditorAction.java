/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 hsz Jakub Chrzanowski <jakub@hsz.mobi>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package mobi.hsz.idea.latex.actions.editor.base;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.psi.LatexFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Editor action - abstract wrap selection action.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.2
 */
public abstract class EditorAction extends AnAction implements DumbAware {

    /**
     * Available editor's types.
     */
    public enum Type {
        BOLD, ITALIC, UNDERLINE,
        ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT,
        IMAGE, MATRIX, TABLE,
    }

    /**
     * Editor action's type.
     */
    private final Type type;

    /**
     * Editor action's name.
     */
    private final String name;

    /**
     * Builds a new instance of {@link EditorAction}.
     */
    EditorAction(@NotNull Type type, @NotNull String name, @NotNull Icon icon) {
        super(name, null, icon);
        this.type = type;
        this.name = name;
    }

    /**
     * Action handler.
     *
     * @param event Carries information on the invocation place
     */
    @Override
    final public void actionPerformed(AnActionEvent event) {
        final VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
        final Project project = event.getData(CommonDataKeys.PROJECT);
        final TextEditor editor = getEditor(event);

        if (virtualFile == null || project == null || editor == null) {
            return;
        }

        actionPerformed(event, project, virtualFile, editor);
    }

    /**
     * Handles actionPerformed event of the editor action item.
     *
     * @param event       action event
     * @param project     current project
     * @param virtualFile current file
     * @param editor      current editor
     */
    protected void actionPerformed(@NotNull AnActionEvent event, @NotNull Project project, @NotNull VirtualFile virtualFile, @NotNull TextEditor editor) {
    }

    @Override
    final public void update(AnActionEvent event) {
        final VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
        final Project project = event.getData(CommonDataKeys.PROJECT);
        final TextEditor editor = getEditor(event);

        if (virtualFile == null || project == null || editor == null) {
            return;
        }

        update(event, project, virtualFile, editor);
    }

    /**
     * Handles update event of the editor action item.
     *
     * @param event       action event
     * @param project     current project
     * @param virtualFile current file
     * @param editor      current editor
     */
    protected void update(AnActionEvent event, @NotNull Project project, @NotNull VirtualFile virtualFile, @NotNull TextEditor editor) {
    }

    /**
     * Returns current active {@link TextEditor} instance or null.
     *
     * @param project current project
     * @return active {@link TextEditor}
     */
    @Nullable
    private static TextEditor getActiveEditor(Project project) {
        if (project != null) {
            FileEditor[] fileEditors = FileEditorManager.getInstance(project).getSelectedEditors();
            for (FileEditor fileEditor : fileEditors) {
                if (fileEditor instanceof TextEditor) {
                    return (TextEditor) fileEditor;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link TextEditor} from witch action was called or null.
     *
     * @param e action event
     * @return active {@link TextEditor}
     */
    @Nullable
    private static TextEditor getEditor(AnActionEvent e) {
        FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (fileEditor instanceof TextEditor) {
            return (TextEditor) fileEditor;
        } else {
            return getActiveEditor(e.getProject());
        }
    }

    /**
     * Returns the currently selected {@link PsiElement}.
     *
     * @param virtualFile Current file.
     * @param project     Current project.
     * @param editor      Current editor.
     * @return Currently selected {@link PsiElement}.
     */
    @Nullable
    protected PsiElement getCurrentElement(@Nullable final VirtualFile virtualFile, @Nullable final Project project, @Nullable TextEditor editor) {
        if (virtualFile == null || project == null || editor == null) {
            return null;
        }

        PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
        if (file == null || !(file instanceof LatexFile)) {
            return null;
        }

        int offset = editor.getEditor().getCaretModel().getOffset();
        return file.findElementAt(offset);
    }

    /**
     * Checks if element matches to the related one.
     *
     * @param element to check
     * @return action's element type
     */
    public boolean isMatching(@NotNull PsiElement element) {
        return false;
    }

    /**
     * Checks if action related type matched to the given element in document.
     *
     * @param element to check
     * @return element matches to the action related type
     */
    @Nullable
    protected PsiElement getMatchedElement(@Nullable PsiElement element) {
        while (element != null && !(element instanceof LatexFile)) {
            if (isMatching(element)) {
                return element;
            }
            element = element.getParent();
        }
        return null;
    }

    /**
     * Runs write action.
     *
     * @param project current project
     * @param action to run
     */
    final protected void runWriteAction(@NotNull final Project project, @NotNull final Runnable action) {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                CommandProcessor.getInstance().executeCommand(project, action, getName(), LatexBundle.NAME);
            }
        });
    }

    /**
     * Returns current editor action's type.
     */
    @NotNull
    public Type getType() {
        return type;
    }

    /**
     * Returns current editor action's name.
     */
    @NotNull
    public String getName() {
        return name;
    }

}
