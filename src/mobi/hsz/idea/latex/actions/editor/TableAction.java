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

package mobi.hsz.idea.latex.actions.editor;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.actions.editor.base.DialogEditorAction;
import mobi.hsz.idea.latex.ui.TableEditorActionDialog;
import mobi.hsz.idea.latex.util.Icons;
import org.jetbrains.annotations.NotNull;

/**
 * Editor action - insert table.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class TableAction extends DialogEditorAction<TableEditorActionDialog> {

    /**
     * Builds a new instance of {@link TableAction}.
     */
    public TableAction() {
        super(Type.TABLE, LatexBundle.message("editor.table"), Icons.Editor.TABLE);
    }

    @Override
    protected TableEditorActionDialog getDialog(@NotNull Project project) {
        return new TableEditorActionDialog(project);
    }

    @Override
    protected Runnable getDialogAction(@NotNull final TableEditorActionDialog dialog, @NotNull final TextEditor editor) {
        return new Runnable() {
            @Override
            public void run() {
                final Document document = editor.getEditor().getDocument();
                final CaretModel caretModel = editor.getEditor().getCaretModel();

                int offset = caretModel.getOffset();
                String content = getContent(dialog.getRows(), dialog.getColumns());

                document.insertString(offset, content);
                caretModel.moveToOffset(offset + content.length());
            }
        };
    }

    @NotNull
    private String getContent(int rows, int columns) {
        StringBuilder sb = new StringBuilder();

        sb.append("\\begin{tabular}{lll}\n");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                sb.append(i);
                sb.append(j);
                sb.append(j == columns ? "\\\\\n" : " & ");
            }
        }
        sb.append("\\end{tabular}");

        return sb.toString();
    }

}
