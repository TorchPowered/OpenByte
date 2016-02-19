package net.openbyte;

import java.io.File;
import java.io.FileOutputStream;

/**
 * File utilities for OpenByte, to make file IO easier.
 */
public class FileUtil {

    /**
     * Sets the content of the file via the format provided.
     *
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
