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

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ui.FormBuilder;
import mobi.hsz.idea.latex.execution.LatexRunSettings;
import mobi.hsz.idea.latex.execution.configuration.LatexRunConfiguration;
import mobi.hsz.idea.latex.execution.ui.LatexParametersPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexRunConfigurationEditor extends SettingsEditor<LatexRunConfiguration> {
    @NotNull
    private final Project project;

    @NotNull
    private final JPanel rootComponent;

    private LatexParametersPanel form;

    public LatexRunConfigurationEditor(@NotNull final Project project) {
        this.project = project;

//
//        myNodeInterpreterField = new NodeJsInterpreterField(project, false);
//        myKarmaPackageDirPathTextFieldWithBrowseButton = createKarmaPackageDirPathTextField(project);
//        myConfigPathTextFieldWithBrowseButton = createConfigurationFileTextField(project);
//        myEnvVarsComponent = new EnvironmentVariablesTextFieldWithBrowseButton();
//        myBrowsers = createBrowsersTextField();
//        JComponent browsersDescription = createBrowsersDescription();
        rootComponent = new FormBuilder()
                .setAlignLabelOnRight(false)
//                .addLabeledComponent(KarmaBundle.message("runConfiguration.config_file.label"), myConfigPathTextFieldWithBrowseButton)
//                .addLabeledComponent(KarmaBundle.message("runConfiguration.browsers.label"), myBrowsers)
//                .addLabeledComponent("", browsersDescription, 0, false)
                .addComponent(new JSeparator(), 8)
//                .addLabeledComponent(KarmaBundle.message("runConfiguration.node_interpreter.label"), myNodeInterpreterField, 8)
//                .addLabeledComponent(KarmaBundle.message("runConfiguration.karma_package_dir.label"), myKarmaPackageDirPathTextFieldWithBrowseButton)
//                .addLabeledComponent(KarmaBundle.message("runConfiguration.environment.label"), myEnvVarsComponent)
                .getPanel();
    }

    @Override
    protected void resetEditorFrom(LatexRunConfiguration configuration) {
        LatexRunSettings settings = configuration.getRunSettings();

    }

    @Override
    protected void applyEditorTo(LatexRunConfiguration configuration) throws ConfigurationException {
        LatexRunSettings.Builder builder = new LatexRunSettings.Builder();
//        builder.setConfigPath(myConfigPathTextFieldWithBrowseButton.getChildComponent().getText());
//        builder.setBrowsers(StringUtil.notNullize(myBrowsers.getText()));
//        builder.setInterpreterRef(myNodeInterpreterField.getInterpreterRef());
//        builder.setEnvData(myEnvVarsComponent.getData());
//        builder.setKarmaPackageDir(myKarmaPackageDirPathTextFieldWithBrowseButton.getChildComponent().getText());
        configuration.setRunSettings(builder.build());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return rootComponent;
    }
}
