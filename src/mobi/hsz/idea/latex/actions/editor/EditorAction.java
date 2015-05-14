/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 hsz Jakub Chrzanowski <jakub@hsz.mobi>
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

package mobi.hsz.idea.latex.actions.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
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

    /** Available editor's types. */
    public enum Type {
        BOLD, ITALIC, UNDERLINE,
        ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT,
    }

    /** Editor action's type. */
    private final Type type;

    /** Editor action's name. */
    private final String name;

    /** Builds a new instance of {@link EditorAction}. */
    public EditorAction(@NotNull Type type, @NotNull String name, @NotNull Icon icon) {
        super(name, null, icon);
        this.type = type;
        this.name = name;
    }

   /**
    * Action handler.
    *
    * @param e Carries information on the invocation place
    */
    @Override
    public final void actionPerformed(AnActionEvent e) {
        final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final TextEditor editor = getEditor(e);

        if (virtualFile == null || project == null || editor == null) {
            return;
        }

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                writeActionPerformed(editor, project, virtualFile);
            }
        });
    }

    @Override
    public void update(AnActionEvent e) {
        final VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final TextEditor editor = getEditor(e);

        if (virtualFile == null || project == null || editor == null) {
            return;
        }

        update(e, editor, project, virtualFile);
    }


    protected abstract void writeActionPerformed(@NotNull TextEditor editor, @NotNull Project project, @NotNull VirtualFile virtualFile);

    protected abstract void update(AnActionEvent e, @NotNull TextEditor editor, @NotNull Project project, @NotNull VirtualFile virtualFile);

    /**
     * Returns current active {@link TextEditor} instance or null.
     *
     * @param project current project
     * @return active {@link TextEditor}
     */
    @Nullable
    public static TextEditor getActiveEditor(Project project) {
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
    public static TextEditor getEditor(AnActionEvent e) {
        FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (fileEditor instanceof TextEditor) {
            return (TextEditor) fileEditor;
        } else {
            return getActiveEditor(e.getProject());
        }
    }

    /** Returns current editor action's type. */
    public Type getType() {
        return type;
    }

    /** Returns current editor action's name.  */
    public String getName() {
        return name;
    }

}
