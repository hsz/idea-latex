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

package mobi.hsz.idea.latex.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.containers.ContainerUtil;
import mobi.hsz.idea.latex.util.Listenable;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Persistent global settings object for the LaTeX plugin.
 *
 * @author Jakub Chrzanowski <jakub@hsz.mobi>
 * @since 0.3.0
 */
@State(
        name = "LatexSettings",
        storages = @Storage(id = "other", file = "$APP_CONFIG$/latex.xml")
)
public class LatexSettings implements PersistentStateComponent<Element>, Listenable<LatexSettings.Listener> {
    /** Settings keys. */
    public enum KEY {
        ROOT("LatexSettings"), VERSION("version");

        private final String key;

        KEY(@NotNull String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return this.key;
        }
    }

    /** Plugin version. */
    private String version;

    /** Listeners list. */
    private final List<Listener> listeners = ContainerUtil.newArrayList();

    /**
     * Get the instance of this service.
     *
     * @return the unique {@link LatexSettings} instance.
     */
    public static LatexSettings getInstance() {
        return ServiceManager.getService(LatexSettings.class);
    }

    /**
     * Get the settings state as a DOM element.
     *
     * @return an ready to serialize DOM {@link Element}.
     * @see {@link #loadState(Element)}
     */
    @Nullable
    @Override
    public Element getState() {
        final Element element = new Element(KEY.ROOT.toString());
        element.setAttribute(KEY.VERSION.toString(), version);

        return element;
    }

    /**
     * Load the settings state from the DOM {@link Element}.
     *
     * @param element the {@link Element} to load values from.
     * @see {@link #getState()}
     */
    @Override
    public void loadState(Element element) {
        String value = element.getAttributeValue(KEY.VERSION.toString());
        if (value != null) version = value;
    }

    /**
     * Returns plugin version.
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets plugin version.
     *
     * @param version of the plugin
     */
    public void setVersion(@NotNull String version) {
        this.notifyOnChange(KEY.VERSION, this.version, version);
        this.version = version;
    }

    /**
     * Add the given listener. The listener will be executed in the containing instance's thread.
     *
     * @param listener listener to add
     */
    @Override
    public void addListener(@NotNull Listener listener) {
        listeners.add(listener);
    }

    /**
     * Remove the given listener.
     *
     * @param listener listener to remove
     */
    @Override
    public void removeListener(@NotNull Listener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies listeners about the changes.
     *
     * @param key      changed property key
     * @param oldValue new value
     * @param newValue new value
     */
    private void notifyOnChange(KEY key, Object oldValue, Object newValue) {
        if (!newValue.equals(oldValue)) {
            for (Listener listener : listeners) {
                listener.onChange(key, newValue);
            }
        }
    }

    /** Listener interface for onChange event. */
    public interface Listener {
        void onChange(@NotNull KEY key, Object value);
    }
}
