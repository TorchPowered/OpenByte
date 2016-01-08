package net.openbyte;

import net.openbyte.data.Files;
import net.openbyte.data.file.OpenProjectSolution;
import net.openbyte.gui.WelcomeFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The entry point class when launching the application.
 */
public class Launch {

    public static final Logger logger = LoggerFactory.getLogger("OpenByte");
    public static final ArrayList<String> projectNames = new ArrayList<String>();
    public static final HashMap<String, OpenProjectSolution> nameToSolution = new HashMap<String, OpenProjectSolution>();

    public static void main(String[] args){
        logger.info("Checking for a workspace folder...");
        if(!Files.WORKSPACE_DIRECTORY.exists()){
            logger.info("Workspace directory not found, creating one.");
            Files.WORKSPACE_DIRECTORY.mkdir();
        }
		try{
			logger.info("Grabbing and applying system look and feel...");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
			logger.info("Something went wrong when applying the look and feel, using the default one...");
			e.printStackTrace();
		}
        logger.info("Showing graphical interface to user...");
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }
}
