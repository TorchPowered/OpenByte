/*
 * This file is part of OpenByte IDE, licensed under the MIT License (MIT).
 *
 * Copyright (c) TorchPowered 2016
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.openbyte.plugin;

import net.openbyte.data.file.PluginDescriptionFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Represents the plugin manager that manages all plugins that are assignable as a AbstractPlugin.
 *
 * @since API Version 1.0
 */
public class PluginManager {
    /**
     * Represents the loaded abstract plugins.
     */
    private static ArrayList<AbstractPlugin> abstractPlugins = new ArrayList<AbstractPlugin>();
    private static HashMap<AbstractPlugin, PluginDescriptionFile> pluginToDescriptionMap = new HashMap<AbstractPlugin, PluginDescriptionFile>();

    /**
     * Retrieves the specified name representation of the plugin.
     *
     * @param pluginName the name representation of the plugin
     * @return the plugin from the name representation
     * @deprecated only for internal use
     */
    public static AbstractPlugin getPlugin(String pluginName) {
        for (AbstractPlugin plugin : abstractPlugins) {
            if(plugin.name().equals(pluginName)) {
                return plugin;
            }
        }
        return null;
    }

    /**
     * Registers and loads a plugin file.
     *
     * @param pluginFile the plugin file which will be loaded and registered.
     * @throws Exception if the plugin is not a valid OpenByte plugin
     * @deprecated only for internal use
     */
    public static void registerAndLoadPlugin(File pluginFile) throws Exception {
        JarFile jarFile = new JarFile(pluginFile);
        JarEntry pluginManifest = jarFile.getJarEntry("plugin.settings");
        InputStream manifestInputStream = jarFile.getInputStream(pluginManifest);

        PluginDescriptionFile descriptionFile = PluginDescriptionFile.getPluginDescription(manifestInputStream);

        String mainClassPath = descriptionFile.getMainClassPath();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{ pluginFile.toURI().toURL() });
        Class theClass = classLoader.loadClass(mainClassPath);
        if(!(AbstractPlugin.class.isAssignableFrom(theClass))) {
            throw new Exception();
        }
        AbstractPlugin plugin = (AbstractPlugin) theClass.newInstance();
        abstractPlugins.add(plugin);
        pluginToDescriptionMap.put(plugin, descriptionFile);
        plugin.enable();
    }

    /**
     * Disables a registered abstract plugin.
     *
     * @param abstractPlugin the abstract plugin that will be disabled
     */
    public static void disablePlugin(AbstractPlugin abstractPlugin) {
        abstractPlugin.disable();
        abstractPlugins.remove(abstractPlugin);
        pluginToDescriptionMap.remove(abstractPlugin);
    }

    /**
     * Retrieves all plugin files in a directory specified.
     *
     * @param directory the directory where the plugin files will be searched from
     * @return the plugin files detected in the directory
     */
    public static File[] getPluginFiles(File directory) {
        return directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
    }

    /**
     * Retrieves all loaded/registered abstract plugins.
     *
     * @return the loaded/registered abstract plugins
     */
    public static AbstractPlugin[] getAbstractPlugins() {
        return abstractPlugins.toArray(new AbstractPlugin[abstractPlugins.size()]);
    }

    /**
     * Retrieves a plugin description file from the specified plugin.
     *
     * @param plugin the plugin that the pluginmanager will find the plugin description file from
     * @return the plugin description file, may be null
     */
    public static PluginDescriptionFile getPluginDescriptionFile(AbstractPlugin plugin) {
        return pluginToDescriptionMap.get(plugin);
    }
}
