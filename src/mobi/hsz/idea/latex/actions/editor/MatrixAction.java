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

import com.intellij.openapi.project.Project;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.actions.editor.base.DialogEditorAction;
import mobi.hsz.idea.latex.ui.MatrixEditorActionDialog;
import mobi.hsz.idea.latex.util.Icons;
import org.jetbrains.annotations.NotNull;

/**
 * Editor action - insert matrix.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class MatrixAction extends DialogEditorAction<MatrixEditorActionDialog> {

    /** Builds a new instance of {@link MatrixAction}. */
    public MatrixAction() {
        super(Type.MATRIX, LatexBundle.message("editor.matrix"), Icons.Editor.MATRIX);
    }

    @NotNull
    @Override
    protected MatrixEditorActionDialog getDialog(@NotNull Project project) {
        return new MatrixEditorActionDialog(project);
    }

    @NotNull
    @Override
    protected String getContent(@NotNull MatrixEditorActionDialog dialog) {
        StringBuilder sb = new StringBuilder();
        int rows = dialog.getRows();
        int columns = dialog.getColumns();
        char bracket = dialog.getBracket().getValue();

        sb.append(String.format("$\\begin{%cmatrix}\n", bracket));
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                sb.append(String.format("%d%d%s", i, j, j == columns ? "\\\\\n" : " & "));
            }
        }
        sb.append(String.format("\\end{%cmatrix}", bracket));

        return sb.toString();
    }

}
