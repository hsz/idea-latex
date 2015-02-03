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

package mobi.hsz.idea.latex.actions.editor;

import com.intellij.openapi.util.Couple;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.2.1
 */
public abstract class WrapEditorAction extends EditorAction {

    /** Builds a new instance of {@link WrapEditorAction}. */
    public WrapEditorAction(@NotNull Type type, @NotNull String name, @NotNull Icon icon) {
        super(type, name, icon);
    }

    /**
     * Returns left part of the wrapping text.
     *
     * @return left part
     */
    public abstract String getLeftText();

    /**
     * Returns right part of the wrapping text.
     *
     * @return right part
     */
    public abstract String getRightText();

    /**
     * Wraps text with the {@link #getLeftText()} and {@link #getRightText()} parts.
     *
     * @param selection selected text
     * @return wrapped selected text
     */
    @NotNull
    @Override
    public String updateText(@NotNull String selection) {
        return getLeftText() + selection + getRightText();
    }

    /**
     * Updates selection.
     *
     * @param start selection start position
     * @param end selection end position
     */
    @Override
    @NotNull
    public Couple<Integer> getSelection(final int start, final int end, final boolean empty) {
        int newStart = start + getLeftText().length();
        int newEnd = empty ? newStart : end + getLeftText().length();

        return new Couple<Integer>(newStart, newEnd);
    }
}
