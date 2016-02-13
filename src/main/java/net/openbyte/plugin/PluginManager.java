package net.openbyte.plugin;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
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
        JarEntry pluginManifest = jarFile.getJarEntry("plugin.pluginproperties");
        InputStream manifestInputStream = jarFile.getInputStream(pluginManifest);
        Properties properties = new Properties();
        properties.load(manifestInputStream);
        String mainClassPath = properties.getProperty("mainclass");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{ pluginFile.toURI().toURL() });
        Class theClass = classLoader.loadClass(mainClassPath);
        if(!(AbstractPlugin.class.isAssignableFrom(theClass))) {
            throw new Exception();
        }
        AbstractPlugin plugin = (AbstractPlugin) theClass.newInstance();
        abstractPlugins.add(plugin);
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
    }
}
