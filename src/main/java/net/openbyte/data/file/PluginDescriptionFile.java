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

package net.openbyte.data.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents a plugin description file.
 */
public class PluginDescriptionFile {
    private String mainClassPath;
    private String author;
    private String logoURL;
    private String iconURL;
    private String description;

    private PluginDescriptionFile(String mainClassPath, String author, String logoURL, String iconURL, String description) {
        this.mainClassPath = mainClassPath;
        this.author = author;
        this.logoURL = logoURL;
        this.iconURL = iconURL;
        this.description = description;
    }

    public static PluginDescriptionFile getPluginDescription(InputStream file) throws Exception {
        Properties properties = new Properties();
        properties.load(file);

        final String main = properties.getProperty("main");
        final String author = properties.getProperty("author");
        final String description = properties.getProperty("description");
        final String iconURL = properties.getProperty("iconurl");
        final String logoURL = properties.getProperty("logourl");

        return new PluginDescriptionFile(main, author, logoURL, iconURL, description);
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public String getMainClassPath() {
        return mainClassPath;
    }
}
