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

import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;
import mobi.hsz.idea.latex.lang.LatexLanguage;
import mobi.hsz.idea.latex.file.LatexFileType;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * {@link FileEditorProvider} implementation to provide {@link LatexFileEditorProvider}.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.1
 */
public class LatexFileEditorProvider implements FileEditorProvider {

    /** The id of the editors provided by this {@link FileEditorProvider}. */
    @NonNls
    public static final String EDITOR_TYPE_ID = LatexLanguage.NAME + "FileEditor";

    /**
     * Checks if file has a proper type for this editor.
     *
     * @param project the project context
     * @param file    the file to be tested
     * @return whether file is a {@link LatexFileType} instance
     */
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        return file.getFileType() instanceof LatexFileType;
    }

    /**
     * Creates a {@link LatexFileEditor} for the given file.
     *
     * @param project the project context
     * @param file    the file for which an editor will be created
     * @return an editor for given file
     */
    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        LatexFileEditor a = new LatexFileEditor(project, FileDocumentManager.getInstance().getDocument(file));

        FileEditorManager m = FileEditorManager.getInstance(project);
//        for (FileEditor editor : m.getEditors(file)) {
            m.addTopComponent(a, new JBLabel("zzzzzzz"));
//        }

        return a;
    }

    /**
     * Disposes the specified <code>editor</code>. It is guaranteed that this method is invoked only for editors
     * created with this provider.
     *
     * @param editor editor to be disposed
     */
    @Override
    public void disposeEditor(@NotNull FileEditor editor) {
        editor.dispose();
    }

    /**
     * Deserializes state from the specified <code>sourceElement</code>.
     *
     * @param sourceElement the source element
     * @param project       the project context
     * @param file          the file
     * @return {@link FileEditorState#INSTANCE}
     */
    @NotNull
    @Override
    public FileEditorState readState(@NotNull Element sourceElement, @NotNull Project project, @NotNull VirtualFile file) {
        return FileEditorState.INSTANCE;
    }

    /**
     * Serializes state into the specified <code>targetElement</code>.
     *
     * @param state         the editor state
     * @param project       the project context
     * @param targetElement teh target element
     */
    @Override
    public void writeState(@NotNull FileEditorState state, @NotNull Project project, @NotNull Element targetElement) {

    }

    /**
     * Get the id of the editors provided by this {@link FileEditorProvider}.
     *
     * @return {@link #EDITOR_TYPE_ID}
     */
    @NotNull
    @Override
    public String getEditorTypeId() {
        return EDITOR_TYPE_ID;
    }

    /**
     * Returns editor's policy.
     *
     * @return policy that specifies how show editor created via this provider be opened
     */
    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
