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
import com.intellij.openapi.util.text.StringUtil;
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

    @NotNull
    @Override
    protected TableEditorActionDialog getDialog(@NotNull Project project) {
        return new TableEditorActionDialog(project);
    }

    @NotNull
    @Override
    protected String getContent(@NotNull TableEditorActionDialog dialog) {
        StringBuilder sb = new StringBuilder();
        int rows = dialog.getRows();
        int columns = dialog.getColumns();
        String alignmentValue = StringUtil.repeatSymbol(dialog.getAlignment().getValue(), columns);
        String tableBorder = dialog.getBorder().isNone() ? "" : "|";
        String cellBorder = dialog.getBorder().isCell() ? "\\hline\n" : "";

        sb.append(String.format("\\begin{tabular}{%s%s%s}\n", tableBorder, alignmentValue, tableBorder));
        sb.append(cellBorder);
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                sb.append(String.format("%d%d%s", i, j, j == columns ? "\\\\\n" : " & "));
            }
            sb.append(cellBorder);
        }
        sb.append("\\end{tabular}");

        return sb.toString();
    }

}
