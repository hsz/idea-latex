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

package mobi.hsz.idea.latex.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import mobi.hsz.idea.latex.lang.LatexLanguage;
import mobi.hsz.idea.latex.ui.LatexFileEditorForm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * {@link FileEditor} implementation that provides an editor for handling LaTeX files.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.1
 */
public class LatexFileEditor implements FileEditor {

//    private final JBPanel panel = new JBPanel();
//    private final Editor editor;

    /** UI form that holds all editor's components. */
    private final LatexFileEditorForm form;

    /**
     * Builds an instance of the {@link LatexFileEditor} class.
     *
     * @param project  current working project
     * @param document current working document
     */
    public LatexFileEditor(@NotNull Project project, @NotNull Document document) {
        form = new LatexFileEditorForm(project, document, this);
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return form.getComponent();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return form.getComponent();
    }

    /**
     * Gets editor's name.
     *
     * @return editor's name
     */
    @NotNull
    @Override
    public String getName() {
        return LatexLanguage.NAME;
    }

    /**
     * Gets editor's internal state.
     *
     * @param level the level
     * @return editor's internal state
     */
    @NotNull
    @Override
    public FileEditorState getState(@NotNull FileEditorStateLevel level) {
        return FileEditorState.INSTANCE;
    }

    /**
     * Applies given state to the editor.
     *
     * @param state cannot be null
     */
    @Override
    public void setState(@NotNull FileEditorState state) {
    }

    /**
     * Checks whether the editor's content is modified in comparison with its file.
     *
     * @return content is modified
     */
    @Override
    public boolean isModified() {
        return false;
    }

    /**
     * Checks whether the editor is valid or not. For some reasons editor can become invalid. For example,
     * text editor becomes invalid when its file is deleted.
     *
     * @return editor is valid or not
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * This method is invoked each time when the editor is selected.
     * This can happen in two cases: editor is selected because the selected file
     * has been changed or editor for the selected file has been changed.
     */
    @Override
    public void selectNotify() {
    }

    /**
     * This method is invoked each time when the editor is deselected.
     */
    @Override
    public void deselectNotify() {
    }

    /**
     * Adds specified listener.
     *
     * @param listener to be added
     */
    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    /**
     * Removes specified listener.
     *
     * @param listener to be removed
     */
    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    /**
     * @return highlighter object to perform background analysis and highlighting activities.
     * Return <code>null</code> if no background highlighting activity necessary for this file editor.
     */
    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    /**
     * The method is optional. Currently is used only by find usages subsystem.
     *
     * @return the location of user focus. Typically it's a caret or any other form of selection start.
     */
    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    /**
     * @return <code>null</code>
     */
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder() {
        return null;
    }

    /**
     * This class marks classes, which require some work done for cleaning up.
     */
    @Override
    public void dispose() {
        Disposer.dispose(this);
//        if (!editor.isDisposed()) {
//            EditorFactory.getInstance().releaseEditor(editor);
//        }
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }
}
