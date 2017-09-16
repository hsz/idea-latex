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

package mobi.hsz.idea.latex.util;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.util.text.StringUtil;
import mobi.hsz.idea.latex.LatexBundle;

/**
 * {@link Utils} class that contains various methods.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3.0
 */
public class Utils {
    /** Private constructor to prevent creating {@link Utils} instance. */
    private Utils() {
    }

    /**
     * Returns Gitignore plugin information.
     *
     * @return {@link IdeaPluginDescriptor}
     */
    public static IdeaPluginDescriptor getPlugin() {
        return PluginManager.getPlugin(PluginId.getId(LatexBundle.PLUGIN_ID));
    }

    /**
     * Returns plugin major version.
     *
     * @return major version
     */
    public static String getMajorVersion() {
        return getVersion().split("\\.")[0];
    }

    /**
     * Returns plugin minor version.
     *
     * @return minor version
     */
    public static String getMinorVersion() {
        return StringUtil.join(getVersion().split("\\."), 0, 2, ".");
    }

    /**
     * Returns plugin version.
     *
     * @return version
     */
    public static String getVersion() {
        return getPlugin().getVersion();
    }
}
