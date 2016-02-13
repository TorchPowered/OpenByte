package net.openbyte.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a abstract plugin. All plugins that will be registered to OpenByte will have to use this class.
 *
 * @since API Version 1.0
 */
public abstract class AbstractPlugin {

    /**
     * Retrieves the name of the plugin.
     *
     * @return the name of the plugin
     */
    public abstract String name();

    /**
     * Retrieves the version of the plugin
     * The OpenByte plugin specification suggests this as a double to make your version checking easier.
     *
     * @return the version of the plugin
     */
    public abstract String version();

    /**
     * This method is called when the plugin is enabled.
     */
    public abstract void enable();

    /**
     * This method is called when the plugin is disabled.
     */
    public abstract void disable();

    /**
     * Retrieves the plugin's logger.
     *
     * @return the plugin's logger
     */
    public Logger logger() {
        return LoggerFactory.getLogger(name());
    }
}
