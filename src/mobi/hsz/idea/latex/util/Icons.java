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

package mobi.hsz.idea.latex.util;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * {@link Icons} class that holds icon resources.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.1
 */
public class Icons {

    public static final Icon FILE = IconLoader.getIcon("/icons/tex.png");

    public static class Editor {

        public static final Icon ALIGN_LEFT = IconLoader.getIcon("/icons/editor/align_left.png");
        public static final Icon ALIGN_CENTER = IconLoader.getIcon("/icons/editor/align_center.png");
        public static final Icon ALIGN_RIGHT = IconLoader.getIcon("/icons/editor/align_right.png");
        public static final Icon BOLD = IconLoader.getIcon("/icons/editor/bold.png");
        public static final Icon IMAGE = IconLoader.getIcon("/icons/editor/image.png");
        public static final Icon ITALIC = IconLoader.getIcon("/icons/editor/italic.png");
        public static final Icon MATRIX = IconLoader.getIcon("/icons/editor/matrix.png");
        public static final Icon TABLE = IconLoader.getIcon("/icons/editor/table.png");
        public static final Icon UNDERLINE = IconLoader.getIcon("/icons/editor/underline.png");

    }

}
