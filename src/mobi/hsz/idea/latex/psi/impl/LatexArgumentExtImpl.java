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
import mobi.hsz.idea.latex.psi.LatexArgument;
import mobi.hsz.idea.latex.psi.LatexArgumentElement;
import mobi.hsz.idea.latex.psi.LatexElementImpl;

/**
 * Abstract LatexArgument implementation.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public abstract class LatexArgumentExtImpl extends LatexElementImpl implements LatexArgumentElement {

    LatexArgumentExtImpl(ASTNode node) {
        super(node);
    }

    /**
     * Returns the Argument element value ignoring the parentheses.
     *
     * @return Argument value.
     */
    @Override
    public String getValue() {
        for (PsiElement child : getChildren()) {
            if (child instanceof LatexArgument) {
                return ((LatexArgument) child).getValue();
            }
        }
        return getText();
    }

}
