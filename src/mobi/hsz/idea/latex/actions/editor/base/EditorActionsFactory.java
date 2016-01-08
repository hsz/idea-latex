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

package mobi.hsz.idea.latex.actions.editor.base;

import mobi.hsz.idea.latex.actions.editor.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Factory for creating LaTeX editor's actions.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.2
 */
public final class EditorActionsFactory {

    /**
     * Creates proper {@link EditorAction} instance basing on given {@link EditorAction.Type}.
     *
     * @return LaTeX editor toolbar action.
     */
    @Nullable
    public static EditorAction create(@NotNull EditorAction.Type type) {
        switch (type) {
            case BOLD:
                return new BoldAction();
            case ITALIC:
                return new ItalicAction();
            case UNDERLINE:
                return new UnderlineAction();
            case ALIGN_LEFT:
                return new AlignLeftAction();
            case ALIGN_CENTER:
                return new AlignCenterAction();
            case ALIGN_RIGHT:
                return new AlignRightAction();
            case IMAGE:
                return new ImageAction();
            case TABLE:
                return new TableAction();

        }
        return null;
    }

}
