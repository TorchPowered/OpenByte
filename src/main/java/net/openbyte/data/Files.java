package net.openbyte.data;

import java.io.File;
import java.io.IOException;

/**
 * Represents important openbyte directories
 */
public class Files {
    /**
     * Represents the current OpenByte workspace directory.
     */
    public static File WORKSPACE_DIRECTORY = new File(System.getProperty("user.home"), ".openbyte");
    /**
     * Represents the plugins directory inside of the workspace directory.
     */
    public static final File PLUGINS_DIRECTORY = new File(WORKSPACE_DIRECTORY, "plugins");

    /**
     * Creates a new file
     * @param fileName the file name
     * @param parentDirectory the parent directory of the file
     * @return the file created
     */
    public static File createNewFile(String fileName, File parentDirectory){
        File file = new File(parentDirectory, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
