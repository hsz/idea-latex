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

package mobi.hsz.idea.latex.toolWindow;

import com.teamdev.jxdocument.SinglePagePresentation;

public class SinglePageViewModel {

    private final int pagesCount;
    private final SinglePagePresentation presentation;

    private int pageIndex;

    public SinglePageViewModel(SinglePagePresentation presentation) {
        this.presentation = presentation;
        this.pagesCount = presentation.getDocument().getPageCount();
        this.setSelectedPageIndex(0);
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getSelectedPageIndex() {
        return pageIndex;
    }

    public void setSelectedPageIndex(int index) {
        if (index < 0 || index >= pagesCount) {
            throw new IllegalArgumentException("The index must be 0 <= index < pages count");
        }
        pageIndex = index;
        presentation.displayPage(pageIndex);
    }

    public void selectNextPage() {
        if (canSelectNextPage()) {
            setSelectedPageIndex(getSelectedPageIndex() + 1);
        }
    }

    public void selectPrevPage() {
        if (canSelectPrevPage()) {
            setSelectedPageIndex(getSelectedPageIndex() - 1);
        }
    }

    public boolean canSelectNextPage() {
        return (getSelectedPageIndex() + 1) < getPagesCount();
    }

    public boolean canSelectPrevPage() {
        return getSelectedPageIndex() > 0;
    }


}
