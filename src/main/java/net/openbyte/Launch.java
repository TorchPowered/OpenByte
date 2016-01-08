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
        logger.info("Searching for project files...");
        File[] projectFiles = Files.WORKSPACE_DIRECTORY.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".openproj");
            }
        });
        logger.info("Compiling project names of the project files...");
        for (File projectFile : projectFiles){
            OpenProjectSolution solution = OpenProjectSolution.getProjectSolutionFromFile(projectFile);
            nameToSolution.put(solution.getProjectName(), solution);
            projectNames.add(solution.getProjectName());
        }
        logger.info("Placing project names inside of recent projects...");
        for (String projectName : projectNames){
            WelcomeFrame.listItems.addElement(projectName);
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
