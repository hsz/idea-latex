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

package mobi.hsz.idea.latex.inspections;

import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import mobi.hsz.idea.latex.lang.LatexLanguage;
import mobi.hsz.idea.latex.psi.LatexText;
import org.jetbrains.annotations.NotNull;

/**
 * {@link SpellcheckingStrategy} implementation for LaTeX language.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexSpellcheckingStrategy extends SpellcheckingStrategy {

    /**
     * Checks if elements is supported.
     *
     * @param element to check
     * @return element is {@link LatexLanguage} instance
     */
    @Override
    public boolean isMyContext(@NotNull PsiElement element) {
        return LatexLanguage.INSTANCE.is(element.getLanguage());
    }

    /**
     * Provides {@link #TEXT_TOKENIZER} for {@link LatexText} elements.
     *
     * @param element to check
     * @return {@link #TEXT_TOKENIZER} for {@link LatexText} or {@link #EMPTY_TOKENIZER}.
     */
    @NotNull
    @Override
    public Tokenizer getTokenizer(PsiElement element) {
        if (element instanceof LatexText) {
            return TEXT_TOKENIZER;
        }
        return EMPTY_TOKENIZER;
    }

}
