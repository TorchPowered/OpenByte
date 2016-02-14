package net.openbyte.data;

import java.io.File;
import java.io.IOException;

/**
 * Represents important openbyte directories
 */
public class Files {
    public static File WORKSPACE_DIRECTORY = new File(System.getProperty("user.home"), ".openbyte");
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
