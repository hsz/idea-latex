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

package mobi.hsz.idea.latex.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.psi.LatexFile;
import mobi.hsz.idea.latex.psi.LatexTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * LaTeX folding builder
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.2
 */
public class LatexFoldingBuilder extends FoldingBuilderEx {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof LatexFile)) {
            return FoldingDescriptor.EMPTY;
        }
        LatexFile file = (LatexFile) root;

        final List<FoldingDescriptor> result = ContainerUtil.newArrayList();

        final List<PsiElement> begins = ContainerUtil.newArrayList();

        if (!quick) {
            PsiTreeUtil.processElements(file, new PsiElementProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element) {
                    IElementType type = element.getNode().getElementType();
                    if (type.equals(LatexTypes.INSTRUCTION_BEGIN)) {
                        begins.add(element);
                    } else if (type.equals(LatexTypes.INSTRUCTION_END)) {
                        PsiElement last = begins.get(begins.size() - 1);
                        TextRange range = new TextRange(last.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
                        result.add(new FoldingDescriptor(last, range));
                        begins.remove(last);
                    }
                    return true;
                }
            });
        }

        return result.toArray(new FoldingDescriptor[result.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return node.getTreeParent().getText() + LatexBundle.message("folding.placeholder");
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }
}
