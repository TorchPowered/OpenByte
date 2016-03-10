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
