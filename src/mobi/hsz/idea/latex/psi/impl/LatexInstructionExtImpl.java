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
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import mobi.hsz.idea.latex.psi.LatexArgument;
import mobi.hsz.idea.latex.psi.LatexElementImpl;
import mobi.hsz.idea.latex.psi.LatexInstruction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mobi.hsz.idea.latex.psi.LatexTypes.IDENTIFIER;
import static mobi.hsz.idea.latex.psi.LatexTypes.IDENTIFIER_BEGIN;
import static mobi.hsz.idea.latex.psi.LatexTypes.IDENTIFIER_END;

/**
 * Abstract {@link LatexInstruction} implementation to handle {@link #getIdentifier()} in instruction subtypes.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public abstract class LatexInstructionExtImpl extends LatexElementImpl implements LatexInstruction {

    /** {@link TokenSet} containing all identifier types. */
    private static final TokenSet IDENTIFIERS = TokenSet.create(IDENTIFIER, IDENTIFIER_BEGIN, IDENTIFIER_END);

    /** Default constructor. */
    LatexInstructionExtImpl(ASTNode node) {
        super(node);
    }

    /** Returns list of identifiers and identifiers subtypes. */
    @NotNull
    public PsiElement getIdentifier() {
        return findNotNullChildByType(IDENTIFIERS);
    }

    /**
     * Returns the {@link LatexInstruction} arguments list.
     *
     * @return {@link LatexArgument} instances list.
     */
    @Override
    @NotNull
    public List<LatexArgument> getArgumentList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, LatexArgument.class);
    }

    /**
     * Returns the {@link LatexInstruction} argument.
     *
     * @return {@link LatexArgument} instance.
     */
    @Nullable
    public LatexArgument getArgument() {
        return findChildByClass(LatexArgument.class);
    }

}
