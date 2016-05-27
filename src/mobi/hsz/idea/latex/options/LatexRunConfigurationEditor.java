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

package mobi.hsz.idea.latex.options;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import mobi.hsz.idea.latex.execution.configuration.LatexRunConfiguration;
import mobi.hsz.idea.latex.execution.ui.LatexParametersPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexRunConfigurationEditor extends SettingsEditor<LatexRunConfiguration> {
    private LatexParametersPanel form;

    public LatexRunConfigurationEditor(@Nullable final Module module) {
        form = new LatexParametersPanel();
        form.setModuleContext(module);
    }

    @Override
    protected void resetEditorFrom(LatexRunConfiguration configuration) {
        form.reset(configuration);
        form.resetLatex(configuration);
    }

    @Override
    protected void applyEditorTo(LatexRunConfiguration configuration) throws ConfigurationException {
        form.applyTo(configuration);
        form.applyLatexTo(configuration);
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return form;
    }

    @Override
    protected void disposeEditor() {
        form = null;
    }
}
