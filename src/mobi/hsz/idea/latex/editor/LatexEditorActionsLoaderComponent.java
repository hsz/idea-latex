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

package mobi.hsz.idea.latex.editor;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import mobi.hsz.idea.latex.file.LatexFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Component loader for editor actions toolbar.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexEditorActionsLoaderComponent extends AbstractProjectComponent {

    /** Constructor. */
    public LatexEditorActionsLoaderComponent(@NotNull final Project project) {
        super(project);
    }

    /**
     * Returns component name.
     *
     * @return component name
     */
    @Override
    @NonNls
    @NotNull
    public String getComponentName() {
        return "LatexEditorActionsLoaderComponent";
    }

    /** Initializes component. */
    @Override
    public void initComponent() {
        myProject.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new LatexEditorManagerListener());
    }

    /** Listener for LaTeX editor manager. */
    private static class LatexEditorManagerListener extends FileEditorManagerAdapter {
        /**
         * Handles file opening event and attaches LaTeX editor component.
         *
         * @param source editor manager
         * @param file   current file
         */
        @Override
        public void fileOpened(@NotNull final FileEditorManager source, @NotNull final VirtualFile file) {
            FileType fileType = file.getFileType();
            if (!(fileType instanceof LatexFileType)) {
                return;
            }

            for (final FileEditor fileEditor : source.getEditors(file)) {
                if (fileEditor instanceof TextEditor) {
                    final LatexEditorActionsWrapper wrapper = new LatexEditorActionsWrapper(fileEditor);
                    final JComponent c = wrapper.getComponent();
                    source.addTopComponent(fileEditor, c);

                    Disposer.register(fileEditor, wrapper);
                    Disposer.register(fileEditor, new Disposable() {
                        @Override
                        public void dispose() {
                            source.removeTopComponent(fileEditor, c);
                        }
                    });
                }
            }
        }
    }

}
