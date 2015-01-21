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

package mobi.hsz.idea.latex.ui;

import com.intellij.ide.ui.customization.CustomActionsSchema;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBSplitter;
import com.intellij.util.EditorPopupHandler;
import mobi.hsz.idea.latex.actions.editor.*;
import mobi.hsz.idea.latex.file.LatexFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static mobi.hsz.idea.latex.actions.editor.EditorAction.Type.*;

/**
 * {@link LatexFileEditorForm} holds whole LaTeX editor UI elements.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.1
 */
public class LatexFileEditorForm implements Disposable {
    /** Current project. */
    private final Project project;

    /** Current document. */
    private final Document document;

    /** File editor. */
    private final FileEditor fileEditor;

    /** Root form element. */
    private JPanel root;

    /** Splitter for editor (left side) and content preview (right side). */
    private JPanel splitter;

    /** Main LaTeX editor. */
    private Editor editor;

    /** Editor's mouse listener. */
    private EditorMouseListener mouseListener;

    /** Builds a new instance of {@link LatexFileEditorForm}. */
    public LatexFileEditorForm(Project project, Document document, FileEditor fileEditor) {
        this.project = project;
        this.document = document;
        this.fileEditor = fileEditor;
    }

    /**
     * Returns root element.
     *
     * @return root panel
     */
    public JPanel getComponent() {
        return root;
    }

    /**
     * Creates and configures UI components.
     */
    private void createUIComponents() {
        editor = createEditor(project, document);
        splitter = new JBSplitter(0.4f);
        ((JBSplitter) splitter).setFirstComponent(editor.getComponent());
//        ((JBSplitter) splitter).setSecondComponent();
    }

    /**
     * Creates tree toolbar panel with actions for working with templates tree.
     *
     * @return action toolbar
     */
    private ActionToolbar createActionsToolbar() {
        DefaultActionGroup actions = new DefaultActionGroup();

        actions.add(EditorActionsFactory.create(BOLD));
        actions.add(EditorActionsFactory.create(ITALIC));
        actions.add(EditorActionsFactory.create(UNDERLINE));
        actions.addSeparator();
        actions.add(EditorActionsFactory.create(ALIGN_LEFT));
        actions.add(EditorActionsFactory.create(ALIGN_CENTER));
        actions.add(EditorActionsFactory.create(ALIGN_RIGHT));

        final ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.EDITOR_TAB, actions, true);
        actionToolbar.setMinimumButtonSize(ActionToolbar.NAVBAR_MINIMUM_BUTTON_SIZE);
        return actionToolbar;
    }

    /**
     * Creates and configures Editor panel for template preview with syntax highlight.
     *
     * @param project  current working project
     * @param document current working document
     * @return editor
     */
    @NotNull
    private Editor createEditor(@NotNull Project project, @NotNull Document document) {
        Editor editor = EditorFactory.getInstance().createEditor(document, project, LatexFileType.INSTANCE, false);
        this.mouseListener = new MouseListener();

        ActionToolbar actionToolbar = createActionsToolbar();
        actionToolbar.setTargetComponent(editor.getComponent());
        editor.setHeaderComponent(actionToolbar.getComponent());
        editor.addEditorMouseListener(mouseListener);

        return editor;
    }

    /**
     * Releases LaTeX editor.
     */
    @Override
    public void dispose() {
        if (!editor.isDisposed()) {
            editor.removeEditorMouseListener(mouseListener);
            EditorFactory.getInstance().releaseEditor(editor);
        }
    }

    /**
     * Returns LaTeX editor.
     *
     * @return LaTeX editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Shows popup menu
     */
    private static final class MouseListener extends EditorPopupHandler {
        @Override
        public void invokePopup(final EditorMouseEvent event) {
            if (!event.isConsumed() && event.getArea() == EditorMouseEventArea.EDITING_AREA) {
                ActionGroup group = (ActionGroup) CustomActionsSchema.getInstance().getCorrectedAction(IdeActions.GROUP_EDITOR_POPUP);
                ActionPopupMenu popupMenu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.EDITOR_POPUP, group);
                MouseEvent e = event.getMouseEvent();
                final Component c = e.getComponent();
                if (c != null && c.isShowing()) {
                    popupMenu.getComponent().show(c, e.getX(), e.getY());
                }
                e.consume();
            }
        }
    }
}
