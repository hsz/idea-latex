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

package mobi.hsz.idea.latex.execution.configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import mobi.hsz.idea.latex.options.LatexRunConfigurationEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexRunConfiguration extends LocatableConfigurationBase implements RefactoringListenerProvider /*,
        /* LatexRunConfigurationParameters, RunConfigurationWithSuppressedDefaultDebugAction */ {
    protected LatexRunConfiguration(Project project, @NotNull ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    /**
     * Returns a listener to handle a rename or move refactoring of the specified PSI element.
     *
     * @param element the element on which a refactoring was invoked.
     * @return the listener to handle the refactoring, or null if the run configuration doesn't care about refactoring of this element.
     */
    @Nullable
    @Override
    public RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        return LatexRunConfigurationRefactoringHandler.getRefactoringElementListener(this, element);
    }

    /**
     * Returns the UI control for editing the run configuration settings. If additional control over validation is required, the object
     * returned from this method may also implement {@link CheckableRunConfigurationEditor}. The returned object
     * can also implement {@link SettingsEditorGroup} if the settings it provides need to be displayed in
     * multiple tabs.
     *
     * @return the settings editor component.
     */
    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new LatexRunConfigurationEditor(getProject());
    }

    /**
     * Prepares for executing a specific instance of the run configuration.
     *
     * @param executor    the execution mode selected by the user (run, debug, profile etc.)
     * @param environment the environment object containing additional settings for executing the configuration.
     * @return the RunProfileState describing the process which is about to be started, or null if it's impossible to start the process.
     */
    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return null;
    }
}
