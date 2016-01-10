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
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileEditor.FileEditor;
import mobi.hsz.idea.latex.actions.editor.base.EditorActionsFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static mobi.hsz.idea.latex.actions.editor.base.EditorAction.Type.*;

/**
 * Wrapper that creates bottom editor component for displaying outer ignore rules.
 * 
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
class LatexEditorActionsWrapper implements Disposable {

    private final ActionToolbar actionToolbar;

    LatexEditorActionsWrapper(@NotNull FileEditor fileEditor) {
        DefaultActionGroup actions = new DefaultActionGroup();

        actions.addAll(
                EditorActionsFactory.create(BOLD),
                EditorActionsFactory.create(ITALIC),
                EditorActionsFactory.create(UNDERLINE),
                Separator.getInstance(),
                EditorActionsFactory.create(ALIGN_LEFT),
                EditorActionsFactory.create(ALIGN_CENTER),
                EditorActionsFactory.create(ALIGN_RIGHT),
                Separator.getInstance(),
                EditorActionsFactory.create(IMAGE),
                EditorActionsFactory.create(TABLE),
                EditorActionsFactory.create(MATRIX)
        );

        actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.EDITOR_TAB, actions, true);
        actionToolbar.setMinimumButtonSize(ActionToolbar.NAVBAR_MINIMUM_BUTTON_SIZE);
        actionToolbar.setTargetComponent(fileEditor.getComponent());
    }

    /**
     * Returns outer panel.
     * 
     * @return outer panel
     */
    @NotNull
    JComponent getComponent() {
        return actionToolbar.getComponent();
    }

    @Override
    public void dispose() {
    }

}
