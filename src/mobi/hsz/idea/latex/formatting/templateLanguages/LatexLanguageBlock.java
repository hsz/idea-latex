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

package mobi.hsz.idea.latex.formatting.templateLanguages;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.ChildAttributes;
import com.intellij.formatting.Indent;
import com.intellij.formatting.templateLanguages.BlockWithParent;
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlockFactory;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.xml.SyntheticBlock;
import com.intellij.psi.tree.IElementType;
import mobi.hsz.idea.latex.psi.LatexTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexLanguageBlock extends TemplateLanguageBlock {
    LatexLanguageBlock(@NotNull TemplateLanguageBlockFactory blockFactory, @NotNull CodeStyleSettings settings,
                       @NotNull ASTNode node, @Nullable List<DataLanguageBlockWrapper> foreignChildren) {
        super(blockFactory, settings, node, foreignChildren);
    }

    @Override
    protected IElementType getTemplateTextElementType() {
        return LatexTypes.TEXT;
    }

    @Override
    public Indent getIndent() {
        // ignore whitespace
        if (myNode.getText().trim().length() == 0) {
            return Indent.getNoneIndent();
        }

        if (getParent() instanceof LatexLanguageBlock && ((LatexLanguageBlock) getParent()).getIndent() == Indent.getNoneIndent()) {
            return Indent.getNormalIndent();
        }

        // any element that is the direct descendant of a foreign block gets an indent
        if (getRealBlockParent() instanceof DataLanguageBlockWrapper) {
            return Indent.getNormalIndent();
        }

        return Indent.getNoneIndent();
    }

    private BlockWithParent getRealBlockParent() {
        // if we can follow the chain of synthetic parent blocks, and if we end up
        // at a real DataLanguage block (i.e. the synthetic blocks didn't lead to an DustFormatterBlock),
        // we're a child of a templated language node and need an indent
        BlockWithParent parent = getParent();
        while (parent instanceof DataLanguageBlockWrapper
                && ((DataLanguageBlockWrapper) parent).getOriginal() instanceof SyntheticBlock) {
            parent = parent.getParent();
        }


        return parent;
    }

    /**
     * TODO implement alignment for "stacked" content.  i.e.:
     * {#foo bar="baz"
     * bat="bam"} <- note the alignment here
     */
    @Override
    public Alignment getAlignment() {
        return null;
    }

    @Override
    public boolean isRequiredRange(TextRange range) {
        // seems our approach doesn't require us to insert any custom DataLanguageBlockFragmentWrapper blocks
        return false;
    }

    /**
     * TODO if/when we implement alignment, update this method to do alignment properly
     * <p>
     * This method handles indent and alignment on Enter.
     */
    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        /**
         * We indent if we're in a TAG_BLOCK (note that this works nicely since Enter can only be invoked
         * INSIDE a block (i.e. after the open block).
         *
         * Also indent if we are wrapped in a block created by the templated language
         */
//        if (myNode.getElementType() == DustTypes.TAG_BLOCK
//                || (getParent() instanceof DataLanguageBlockWrapper
//                && (myNode.getElementType() != DustTypes.STATEMENTS || myNode.getTreeNext() instanceof PsiErrorElement))) {
        return new ChildAttributes(Indent.getNormalIndent(), null);
//        } else {
//            return new ChildAttributes(Indent.getNoneIndent(), null);
//        }
    }
}
