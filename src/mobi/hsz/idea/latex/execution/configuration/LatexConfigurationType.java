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

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.RunManagerEx;
import com.intellij.execution.configuration.ConfigurationFactoryEx;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.openapi.project.Project;
import mobi.hsz.idea.latex.util.Icons;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3
 */
public class LatexConfigurationType extends ConfigurationTypeBase {
    protected LatexConfigurationType() {
        super("LatexConfigurationType", "LaTeX", "LaTeX run configuration", Icons.FILE);
        addFactory(new LatexConfigurationFactory(this));
    }

    @NotNull
    public static LatexConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(LatexConfigurationType.class);
    }

    private static class LatexConfigurationFactory extends ConfigurationFactoryEx {
        LatexConfigurationFactory(LatexConfigurationType configurationType) {
            super(configurationType);
        }

        @Override
        public void onNewConfigurationCreated(@NotNull RunConfiguration configuration) {
            //the last param has to be false because we do not want a fallback to the template (we're creating it right now) (avoiding a SOE)
            RunManagerEx.getInstanceEx(configuration.getProject()).setBeforeRunTasks(configuration, Collections.<BeforeRunTask>emptyList(), false);
        }

        @NotNull
        @Override
        public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
            LatexRunConfiguration configuration = new LatexRunConfiguration("", new RunConfigurationModule(project), this);
//            configuration.setInterpreterPath(BashInterpreterDetection.instance().findBestLocation());

            return configuration;
        }
    }
}
