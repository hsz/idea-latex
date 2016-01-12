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

package mobi.hsz.idea.latex.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import mobi.hsz.idea.latex.LatexBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MatrixEditorActionDialog extends DialogWrapper {

    public enum Bracket {
        NONE('\0'), PARENTHESES('p'), BRACKETS('b'), BRACES('B'), SINGLE_LINE('v'), DOUBLE_LINE('V');

        private final char value;

        Bracket(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        @Override
        public String toString() {
            return LatexBundle.message("form.bracket." + name().toLowerCase());
        }
    }

    private JPanel panel;
    private JSpinner rows;
    private JSpinner columns;
    private JComboBox<Bracket> bracket;

    public MatrixEditorActionDialog(@NotNull Project project) {
        super(project);
        setTitle(LatexBundle.message("editor.matrix.dialog.title"));

        rows.setModel(new SpinnerNumberModel(2, 1, null, 1));
        columns.setModel(new SpinnerNumberModel(2, 1, null, 1));
        bracket.setModel(new DefaultComboBoxModel<Bracket>(Bracket.values()));

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }

    public int getRows() {
        return (Integer) rows.getValue();
    }

    public int getColumns() {
        return (Integer) columns.getValue();
    }

    @NotNull
    public Bracket getBracket() {
        return (Bracket) bracket.getSelectedItem();
    }

}
