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

package mobi.hsz.idea.latex.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.psi.*;
import mobi.hsz.idea.latex.psi.impl.LatexSectionExtImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * LaTeX folding builder. Handles {@link LatexTypes#SECTION} folding.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.2
 */
public class LatexFoldingBuilder implements FoldingBuilder {

    /** Descriptors collection. */
    private final List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

    /** {@link LatexVisitor} instance to walk over all sections in the {@link PsiElement}. */
    private final PsiElementVisitor visitor = new LatexVisitor() {
        @Override
        public void visitSection(@NotNull LatexSection section) {
            LatexInstructionBegin begin = ((LatexSectionExtImpl) section).getBeginInstruction();
            LatexInstructionEnd end = ((LatexSectionExtImpl) section).getEndInstruction();

            if (begin == null || end == null) {
                return;
            }

            int startOffset = begin.getTextOffset();
            int endOffset = end.getTextOffset() + end.getTextLength();
            if (endOffset - startOffset > 0) {
                descriptors.add(new FoldingDescriptor(section, new TextRange(startOffset, endOffset)));
            }

            section.acceptChildren(visitor);
        }
    };

    /**
     * Builds the folding regions for the specified node in the AST tree and its children.
     * Note that you can have several folding regions for one AST node, i.e. several {@link FoldingDescriptor} with similar AST node.
     *
     * @param node     the node for which folding is requested.
     * @param document the document for which folding is built. Can be used to retrieve line
     *                 numbers for folding regions.
     * @return the array of folding descriptors.
     */
    @NotNull
    public FoldingDescriptor[] buildFoldRegions(@NotNull final ASTNode node, @NotNull final Document document) {
        descriptors.clear();
        node.getPsi().acceptChildren(visitor);

        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    /**
     * Returns the text which is displayed in the editor for the folding region related to the
     * specified node when the folding region is collapsed.
     *
     * @param node the node for which the placeholder text is requested.
     * @return the placeholder text.
     */
    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return node.getFirstChildNode().getText() + LatexBundle.message("folding.placeholder");
    }

    /**
     * Returns the default collapsed state for the folding region related to the specified node.
     *
     * @param node the node for which the collapsed state is requested.
     * @return true if the region is collapsed by default, false otherwise.
     */
    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }

}
