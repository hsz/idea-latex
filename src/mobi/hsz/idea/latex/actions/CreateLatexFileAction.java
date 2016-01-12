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

package mobi.hsz.idea.latex.actions;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateFromTemplateAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.lang.LatexLanguage;
import mobi.hsz.idea.latex.psi.LatexFile;
import mobi.hsz.idea.latex.util.Icons;
import org.jetbrains.annotations.Nullable;

/**
 * New file action
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class CreateLatexFileAction extends CreateFromTemplateAction<LatexFile> implements DumbAware {

    public CreateLatexFileAction() {
        super(LatexBundle.message("action.create.file"), LatexBundle.message("action.create.file.description"), Icons.FILE);
    }

    @Override
    protected String getDefaultTemplateProperty() {
        return LatexTemplates.LATEX_EMPTY;
    }

    @Nullable
    @Override
    protected LatexFile createFile(String name, String templateName, PsiDirectory dir) {
        String filename = name.endsWith("." + LatexLanguage.EXTENSION) ? name : name + "." + LatexLanguage.EXTENSION;
        return LatexTemplatesFactory.createFromTemplate(dir, name, filename, templateName, true);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder
                .setTitle(LatexBundle.message("action.create.file"))
                .addKind(LatexBundle.message("action.create.file.kind.empty"), Icons.FILE, LatexTemplates.LATEX_EMPTY)
                .addKind(LatexBundle.message("action.create.file.kind.article"), Icons.FILE, LatexTemplates.LATEX_ARTICLE)
                .addKind(LatexBundle.message("action.create.file.kind.report"), Icons.FILE, LatexTemplates.LATEX_REPORT)
                .addKind(LatexBundle.message("action.create.file.kind.book"), Icons.FILE, LatexTemplates.LATEX_BOOK)
                .addKind(LatexBundle.message("action.create.file.kind.letter"), Icons.FILE, LatexTemplates.LATEX_LETTER)
                .addKind(LatexBundle.message("action.create.file.kind.presentation"), Icons.FILE, LatexTemplates.LATEX_PRESENTATION);
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return LatexBundle.message("action.create.file");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CreateLatexFileAction;
    }

}
