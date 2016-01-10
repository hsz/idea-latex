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
import mobi.hsz.idea.latex.ui.ImageEditorActionDialog;
import mobi.hsz.idea.latex.util.Icons;
import org.jetbrains.annotations.NotNull;

/**
 * Editor action - insert image.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class ImageAction extends DialogEditorAction<ImageEditorActionDialog> {

    /** Builds a new instance of {@link ImageAction}. */
    public ImageAction() {
        super(Type.IMAGE, LatexBundle.message("editor.image"), Icons.Editor.IMAGE);
    }

    @NotNull
    @Override
    protected ImageEditorActionDialog getDialog(@NotNull Project project) {
        return new ImageEditorActionDialog(project);
    }

    @NotNull
    @Override
    protected String getContent(@NotNull ImageEditorActionDialog dialog) {
        StringBuilder sb = new StringBuilder();
        String path = dialog.getPath();
        String caption = dialog.getCaption();
        String label = dialog.getLabel();

        sb.append("\\begin{figure}\n");
        sb.append("\\centering\n");
        sb.append("\\includegraphics");
        sb.append(String.format("{%s}\n", path));

        if (!StringUtil.isEmpty(caption)) {
            sb.append(String.format("\\caption{%s}\n", caption));
        }
        if (!StringUtil.isEmpty(label)) {
            sb.append(String.format("\\label{%s}\n", label));
        }

        sb.append("\\end{figure}");

        return sb.toString();
    }

}
