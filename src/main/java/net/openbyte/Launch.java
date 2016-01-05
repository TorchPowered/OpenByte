package net.openbyte;

import net.openbyte.data.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;

/**
 * The entry point class when launching the application.
 */
public class Launch {

    public static final Logger logger = LoggerFactory.getLogger("OpenByte");

    public static void main(String[] args){
        logger.info("Checking for a workspace folder...");
        if(!Files.WORKSPACE_DIRECTORY.exists()){
            logger.info("Workspace directory not found, creating one.");
            Files.WORKSPACE_DIRECTORY.mkdir();
        }
        logger.info("Searching for project files...");
        File[] projectFiles = Files.WORKSPACE_DIRECTORY.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".openproj");
            }
        });
    }
}
