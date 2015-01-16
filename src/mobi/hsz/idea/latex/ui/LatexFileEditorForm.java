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

import com.intellij.ide.CommonActionsManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBPanel;
import mobi.hsz.idea.latex.file.LatexFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * {@link LatexFileEditorForm} holds whole LaTeX editor UI elements.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.1
 */
public class LatexFileEditorForm {
    /** Current project. */
    private final Project project;

    /** Current document. */
    private final Document document;
    private final FileEditor fileEditor;

    /** Root form element. */
    private JPanel root;

    /** Action toolbar panel. */
    private JPanel toolbarPanel;

    /** Splitter for editor (left side) and content preview (right side). */
    private JPanel splitter;

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
        toolbarPanel = new JBPanel(new BorderLayout());

        Editor editor = createEditor(project, document);
        JComponent toolbar = createActionsToolbar(editor.getComponent()).getComponent();

        toolbarPanel.add(toolbar);

        splitter = new JBSplitter(0.4f);
        ((JBSplitter) splitter).setFirstComponent(editor.getComponent());
//        ((JBSplitter) splitter).setSecondComponent();
    }


    /**
     * Creates tree toolbar panel with actions for working with templates tree.
     *
     * @param target templates tree
     * @return action toolbar
     */
    private ActionToolbar createActionsToolbar(JComponent target) {
        final CommonActionsManager actionManager = CommonActionsManager.getInstance();

        DefaultActionGroup actions = new DefaultActionGroup();
        actions.add(actionManager.createHelpAction("xxx"));

        final ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, actions, true);
        actionToolbar.setMinimumButtonSize(ActionToolbar.NAVBAR_MINIMUM_BUTTON_SIZE);
        actionToolbar.setTargetComponent(target);
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
    private static Editor createEditor(@NotNull Project project, @NotNull Document document) {
        Editor editor = EditorFactory.getInstance().createEditor(document, project, LatexFileType.INSTANCE, false);

//        EditorColorsScheme colorsScheme = editor.getColorsScheme();
//        colorsScheme.setColor(EditorColors.CARET_ROW_COLOR, null);
        return editor;
    }
}
