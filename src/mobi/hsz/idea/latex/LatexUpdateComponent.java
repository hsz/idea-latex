/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 hsz Jakub Chrzanowski <jakub@hsz.mobi>
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

package mobi.hsz.idea.latex;

import com.intellij.notification.*;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import mobi.hsz.idea.latex.lang.LatexLanguage;
import mobi.hsz.idea.latex.util.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * {@link ProjectComponent} instance to display plugin's update information.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3.0
 */
public class LatexUpdateComponent extends AbstractProjectComponent {
    /** {@link LatexApplicationComponent} instance. */
    private LatexApplicationComponent application;

    /** Constructor. */
    protected LatexUpdateComponent(Project project) {
        super(project);
    }

    /** Component initialization method. */
    @Override
    public void initComponent() {
        application = LatexApplicationComponent.getInstance();
    }

    /** Component dispose method. */
    @Override
    public void disposeComponent() {
    }

    /**
     * Returns component's name.
     *
     * @return component's name
     */
    @NotNull
    @Override
    public String getComponentName() {
        return "LatexUpdateComponent";
    }

    /** Method called when project is opened. */
    @Override
    public void projectOpened() {
        if (application.isUpdated() && !application.isUpdateNotificationShown()) {
            application.setUpdateNotificationShown(true);
            NotificationGroup group = new NotificationGroup(LatexLanguage.GROUP, NotificationDisplayType.TOOL_WINDOW, true);
            Notification notification = group.createNotification(
                    LatexBundle.message("update.title", Utils.getVersion()),
                    LatexBundle.message("update.content"),
                    NotificationType.INFORMATION,
                    NotificationListener.URL_OPENING_LISTENER
            );
            Notifications.Bus.notify(notification, myProject);
        }
    }

    /** Method called when project is closed. */
    @Override
    public void projectClosed() {
    }
}
