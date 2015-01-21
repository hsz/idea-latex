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

import com.intellij.openapi.editor.Editor;

/**
 * Factory for creating LaTeX editor's actions.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.2
 */
public class EditorActionsFactory {

    /** LaTeX editor. */
    private final Editor editor;

    /** Builds a new instance of {@link EditorActionsFactory}. */
    public EditorActionsFactory(Editor editor) {
        this.editor = editor;
    }

    /** Creates proper {@link EditorAction} instance basing on given {@link EditorAction.Type}. */
    public EditorAction create(EditorAction.Type type) {
        EditorAction action = null;

        switch (type) {
            case BOLD:
                action = new BoldAction();
                break;
            case ITALIC:
                action = new ItalicAction();
                break;
            case UNDERLINE:
                action = new UnderlineAction();
                break;
        }

        return action;
    }

}
