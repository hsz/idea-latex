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

package mobi.hsz.idea.latex.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import mobi.hsz.idea.latex.psi.*;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract {@link LatexSection} implementation to provide shorthands for accessing {@link LatexInstructionBegin}
 * and {@link LatexInstructionEnd}.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public abstract class LatexSectionExtImpl extends LatexElementImpl implements LatexSection {

    LatexSectionExtImpl(ASTNode node) {
        super(node);
    }

    /**
     * Returns {@link LatexInstructionBegin} instance from given {@link LatexTypes#SECTION} element.
     *
     * @return begin instruction
     */
    @Nullable
    public LatexInstructionBegin getBeginInstruction() {
        return PsiTreeUtil.getChildOfType(this, LatexInstructionBegin.class);
    }

    /**
     * Returns {@link LatexInstructionEnd} instance from given {@link LatexTypes#SECTION} element.
     *
     * @return end instruction
     */
    @Nullable
    public LatexInstructionEnd getEndInstruction() {
        return PsiTreeUtil.getChildOfType(this, LatexInstructionEnd.class);
    }

}
