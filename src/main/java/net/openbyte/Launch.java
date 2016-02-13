package net.openbyte;

import net.openbyte.data.Files;
import net.openbyte.data.file.OpenProjectSolution;
import net.openbyte.gui.WelcomeFrame;
import net.openbyte.plugin.PluginManager;
import org.apache.commons.io.FilenameUtils;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The entry point class when launching the application.
 */
public class Launch {

    public static final Logger logger = LoggerFactory.getLogger("OpenByte");
    public static final ArrayList<String> projectNames = new ArrayList<String>();
    public static final HashMap<String, OpenProjectSolution> nameToSolution = new HashMap<String, OpenProjectSolution>();
    public static final double CURRENT_VERSION = 0.92;

    public static void main(String[] args){
        logger.info("Checking for a new version...");
        try {
            GitHub gitHub = new GitHubBuilder().withOAuthToken("e5b60cea047a3e44d4fc83adb86ea35bda131744 ").build();
            GHRepository repository = gitHub.getUser("PizzaCrust").getRepository("OpenByte");
            for (GHRelease release : repository.listReleases()) {
                double releaseTag = Double.parseDouble(release.getTagName());
                if(CURRENT_VERSION < releaseTag) {
                    logger.info("Version " + releaseTag + " has been released.");
                    JOptionPane.showMessageDialog(null, "Please update OpenByte to " + releaseTag + " at https://github.com/PizzaCrust/OpenByte.", "Update", JOptionPane.WARNING_MESSAGE);
                } else {
                    logger.info("OpenByte is at the latest version.");
                }
            }
        } catch (Exception e) {
            logger.error("Failed to connect to GitHub.");
            e.printStackTrace();
        }
        logger.info("Checking for a workspace folder...");
        if(!Files.WORKSPACE_DIRECTORY.exists()){
            logger.info("Workspace directory not found, creating one.");
            Files.WORKSPACE_DIRECTORY.mkdir();
        }
        logger.info("Checking for a plugins folder...");
        if(!Files.PLUGINS_DIRECTORY.exists()) {
            logger.info("Plugins directory not found, creating one.");
            Files.PLUGINS_DIRECTORY.mkdir();
        }
		try{
			logger.info("Grabbing and applying system look and feel...");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
			logger.info("Something went wrong when applying the look and feel, using the default one...");
			e.printStackTrace();
		}
        logger.info("Detecting plugin files...");
        File[] pluginFiles = PluginManager.getPluginFiles(Files.PLUGINS_DIRECTORY);
        logger.info("Detected " + pluginFiles.length + " in the plugins directory!");
        logger.info("Beginning load/register plugin process...");
        for (File pluginFile : pluginFiles) {
            logger.info("Loading file " + FilenameUtils.removeExtension(pluginFile.getName()) + "...");
            try {
                PluginManager.registerAndLoadPlugin(pluginFile);
            } catch (Exception e) {
                logger.error("Failed to load file " + FilenameUtils.removeExtension(pluginFile.getName()) + "!");
                e.printStackTrace();
            }
        }
        logger.info("All plugin files were loaded/registered to OpenByte.");
        logger.info("Showing graphical interface to user...");
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }
}
