package net.openbyte;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Some file utilities.
 */
public class FileUtil {

    /**
     * Sets the content of the file
     * @param format the format that the file with be formatted
     * @param file the file that will be formatted
     */
    public static void format(String format, File file){
        byte[] formatBytes = format.getBytes();
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(formatBytes);
            stream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
