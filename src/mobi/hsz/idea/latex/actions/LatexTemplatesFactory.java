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

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.ide.fileTemplates.*;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.file.LatexFileType;
import mobi.hsz.idea.latex.psi.LatexFile;
import mobi.hsz.idea.latex.util.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * LaTeX files templates factory.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexTemplatesFactory implements FileTemplateGroupDescriptorFactory {

    @NonNls
    public static final String[] TEMPLATES = {
            LatexTemplates.LATEX_EMPTY, LatexTemplates.LATEX_ARTICLE, LatexTemplates.LATEX_REPORT,
            LatexTemplates.LATEX_BOOK, LatexTemplates.LATEX_LETTER, LatexTemplates.LATEX_PRESENTATION,
    };

    private final ArrayList<String> myCustomTemplates = new ArrayList<String>();

    private static class LatexTemplatesFactoryHolder {
        private static final LatexTemplatesFactory myInstance = new LatexTemplatesFactory();
    }

    public static LatexTemplatesFactory getInstance() {
        return LatexTemplatesFactoryHolder.myInstance;
    }

    public void registerCustromTemplates(String... templates) {
        Collections.addAll(myCustomTemplates, templates);
    }

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor(LatexBundle.message("file.template.group.title"), Icons.FILE);
        final FileTypeManager fileTypeManager = FileTypeManager.getInstance();

        for (String template : TEMPLATES) {
            group.addTemplate(new FileTemplateDescriptor(template, fileTypeManager.getFileTypeByFileName(template).getIcon()));
        }

        // register custom templates
        for (String template : getInstance().getCustomTemplates()) {
            group.addTemplate(new FileTemplateDescriptor(template, fileTypeManager.getFileTypeByFileName(template).getIcon()));
        }

        return group;
    }

    public static LatexFile createFromTemplate(@NotNull final PsiDirectory directory, @NotNull final String name,
                                               @NotNull String fileName, @NotNull String templateName, boolean allowReformatting,
                                               @NonNls String... parameters) throws IncorrectOperationException {
        final FileTemplate template = FileTemplateManager.getInstance(directory.getProject()).getInternalTemplate(templateName);

        Project project = directory.getProject();

        Properties properties = new Properties(FileTemplateManager.getInstance(project).getDefaultProperties());
//        JavaTemplateUtil.setPackageNameAttribute(properties, directory);
//        properties.setProperty(NAME_TEMPLATE_PROPERTY, name);
//        properties.setProperty(LOW_CASE_NAME_TEMPLATE_PROPERTY, name.substring(0, 1).toLowerCase() + name.substring(1));
        for (int i = 0; i < parameters.length; i += 2) {
            properties.setProperty(parameters[i], parameters[i + 1]);
        }

        String text;
        try {
            text = template.getText(properties);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance(project).internalTemplateToSubject(templateName), e);
        }

        final PsiFileFactory factory = PsiFileFactory.getInstance(project);
        PsiFile file = factory.createFileFromText(fileName, LatexFileType.INSTANCE, text);

        file = (PsiFile) directory.add(file);
        if (file != null && allowReformatting && template.isReformatCode()) {
            new ReformatCodeProcessor(project, file, null, false).run();
        }

        return (LatexFile) file;
    }

    public String[] getCustomTemplates() {
        return ArrayUtil.toStringArray(myCustomTemplates);
    }

}
