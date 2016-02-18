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
