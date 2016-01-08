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

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Toggleable;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public abstract class WrapEditorAction extends EditorAction implements Toggleable {

    /**
     * Builds a new instance of {@link WrapEditorAction}.
     */
    public WrapEditorAction(@NotNull Type type, @NotNull String name, @NotNull Icon icon) {
        super(type, name, icon);
    }

    /**
     * Writes content to the current document.
     *
     * @param event       action event
     * @param project     current project
     * @param virtualFile current file
     * @param editor      current editor
     */
    @Override
    protected void actionPerformed(@NotNull AnActionEvent event, @NotNull Project project, @NotNull VirtualFile virtualFile, @NotNull final TextEditor editor) {
        PsiElement element = getCurrentElement(virtualFile, project, editor);
        final PsiElement matched = getMatchedElement(element);
        runWriteAction(project, new Runnable() {
            @Override
            public void run() {
                if (matched == null) {
                    wrap(editor);
                } else {
                    unwrap(editor, matched);
                }
            }
        });
    }

    /**
     * Updates the state of the action.
     *
     * @param event Carries information on the invocation place and data available
     */
    @Override
    protected void update(AnActionEvent event, @NotNull Project project, @NotNull VirtualFile virtualFile, @NotNull TextEditor editor) {
        PsiElement element = getCurrentElement(virtualFile, project, editor);
        PsiElement matched = getMatchedElement(element);

        event.getPresentation().putClientProperty(SELECTED_PROPERTY, matched != null);
    }

    /**
     * Wraps selection.
     *
     * @param editor Current editor.
     */
    private void wrap(@NotNull TextEditor editor) {
        final Document document = editor.getEditor().getDocument();
        final SelectionModel selectionModel = editor.getEditor().getSelectionModel();
        final CaretModel caretModel = editor.getEditor().getCaretModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        final String text = StringUtil.notNullize(selectionModel.getSelectedText());

        String newText = getLeftText() + text + getRightText();
        int newStart = start + getLeftText().length();
        int newEnd = StringUtil.isEmpty(text) ? newStart : end + getLeftText().length();

        document.replaceString(start, end, newText);
        selectionModel.setSelection(newStart, newEnd);
        caretModel.moveToOffset(newEnd);
    }

    /**
     * Unwraps selection.
     *
     * @param editor  Current editor.
     * @param matched Matched PSI element.
     */
    private void unwrap(@NotNull final TextEditor editor, @NotNull final PsiElement matched) {
        final Document document = editor.getEditor().getDocument();
        final SelectionModel selectionModel = editor.getEditor().getSelectionModel();
        final CaretModel caretModel = editor.getEditor().getCaretModel();

        final int start = matched.getTextRange().getStartOffset();
        final int end = matched.getTextRange().getEndOffset();
        final String text = StringUtil.notNullize(matched.getText());

        String newText = StringUtil.trimEnd(StringUtil.trimStart(text, getLeftText()), getRightText());
        int newStart = selectionModel.getSelectionStart() - getLeftText().length();
        int newEnd = selectionModel.getSelectionEnd() - getLeftText().length();

        document.replaceString(start, end, newText);

        selectionModel.setSelection(newStart, newEnd);
        caretModel.moveToOffset(newEnd);
    }

    /**
     * Returns left part of the wrapping text.
     *
     * @return left part
     */
    @NotNull
    public abstract String getLeftText();

    /**
     * Returns right part of the wrapping text.
     *
     * @return right part
     */
    @NotNull
    public abstract String getRightText();

}
