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
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import mobi.hsz.idea.latex.editor.LatexFileEditor;
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
    
    /** Builds a new instance of {@link EditorAction}. */
    public EditorAction(@NotNull Type type, @NotNull String name, @NotNull Icon icon) {
        super(name, null, icon);
        this.type = type;
    }

   /**
    * Action handler.
    *
    * @param e Carries information on the invocation place
    */
    @Override
    public final void actionPerformed(AnActionEvent e) {
        final LatexFileEditor editor = getLatexEditor(e);

        if (editor != null) {
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    CommandProcessor.getInstance().runUndoTransparentAction(new Runnable() {
                        @Override
                        public void run() {
                            Document document = editor.getEditor().getDocument();
                            SelectionModel selectionModel = editor.getEditor().getSelectionModel();
                            CaretModel caretModel = editor.getEditor().getCaretModel();

                            final int start = selectionModel.getSelectionStart();
                            final int end = selectionModel.getSelectionEnd();
                            final String text = StringUtil.notNullize(selectionModel.getSelectedText());

                            document.replaceString(start, end, updateText(text));

                            Couple<Integer> selection = getSelection(start, end, StringUtil.isEmpty(text));
                            selectionModel.setSelection(selection.getFirst(), selection.getSecond());

                            caretModel.moveToOffset(selection.getSecond());
                        }
                    });
                }
            });
        }
    }

    /**
     * Replaces selected text with returned string.
     *
     * @param selection selected text
     */
    @NotNull
    public abstract String updateText(@NotNull String selection);

    /**
     * Updates selection.
     *
     * @param start selection start position
     * @param end selection end position
     */
    @NotNull
    public abstract Couple<Integer> getSelection(final int start, final int end, final boolean empty);

    /**
     * Returns current active {@link LatexFileEditor} instance or null.
     *
     * @param project current project
     * @return active {@link LatexFileEditor}
     */
    @Nullable
    public static LatexFileEditor getActiveLatexEditor(Project project) {
        if (project != null) {
            FileEditor[] fileEditors = FileEditorManager.getInstance(project).getSelectedEditors();
            for (FileEditor fileEditor : fileEditors) {
                if (fileEditor instanceof LatexFileEditor) {
                    return (LatexFileEditor) fileEditor;
                }
            }
        }
        return null;
    }

    /**
     * Returns {@link LatexFileEditor} from witch action was called or null.
     *
     * @param e action event
     * @return active {@link LatexFileEditor}
     */
    @Nullable
    public static LatexFileEditor getLatexEditor(AnActionEvent e) {
        FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (fileEditor instanceof LatexFileEditor) {
            return (LatexFileEditor) fileEditor;
        } else {
            return getActiveLatexEditor(e.getProject());
        }
    }

    /** Returns current editor action's type. */
    public Type getType() {
        return type;
    }

}
