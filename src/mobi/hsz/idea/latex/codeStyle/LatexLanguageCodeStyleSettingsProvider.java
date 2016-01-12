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

package mobi.hsz.idea.latex.codeStyle;

import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import mobi.hsz.idea.latex.lang.LatexLanguage;
import mobi.hsz.idea.latex.util.Resources;
import org.jetbrains.annotations.NotNull;

/**
 * LaTeX {@link LanguageCodeStyleSettingsProvider} implementation.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

    @NotNull
    @Override
    public Language getLanguage() {
        return LatexLanguage.INSTANCE;
    }

    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings defaultSettings = new CommonCodeStyleSettings(LatexLanguage.INSTANCE);
        defaultSettings.initIndentOptions();
//        defaultSettings.SPACE_WITHIN_BRACES = true;
//        defaultSettings.KEEP_SIMPLE_CLASSES_IN_ONE_LINE = true;
//        defaultSettings.KEEP_SIMPLE_METHODS_IN_ONE_LINE = true;
        return defaultSettings;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        switch (settingsType) {
            case INDENT_SETTINGS:
            case SPACING_SETTINGS:
            case WRAPPING_AND_BRACES_SETTINGS:
            case BLANK_LINES_SETTINGS:
                return Resources.getResourceContent("/codeStyle/preview/" + settingsType.name().toLowerCase() + ".tex.ft");
            default: return "foo";
        }
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }

}
