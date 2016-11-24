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

package mobi.hsz.idea.latex.execution;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.refactoring.listeners.UndoRefactoringElementAdapter;
import mobi.hsz.idea.latex.execution.configuration.LatexRunConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexRunConfigurationRefactoringHandler {
    @Nullable
    public static RefactoringElementListener getRefactoringElementListener(@NotNull LatexRunConfiguration configuration,
                                                                           @Nullable PsiElement element) {
        VirtualFile fileAtElement = PsiUtilBase.asVirtualFile(element);
        if (fileAtElement == null) {
            return null;
        }
        LatexRunSettings settings = configuration.getRunSettings();
        String path = fileAtElement.getPath();
        String configPath = FileUtil.toSystemIndependentName(settings.getConfigPath());
        if (configPath.equals(path)) {
            return new FilePathRefactoringElementListener(configuration);
        }
        return null;
    }

    private static class FilePathRefactoringElementListener extends UndoRefactoringElementAdapter {
        private final LatexRunConfiguration configuration;

        private FilePathRefactoringElementListener(@NotNull LatexRunConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        protected void refactored(@NotNull PsiElement element, @Nullable String oldQualifiedName) {
            VirtualFile newFile = PsiUtilBase.asVirtualFile(element);
            if (newFile != null) {
                configuration.setConfigFilePath(newFile.getPath());
            }
        }
    }
}
