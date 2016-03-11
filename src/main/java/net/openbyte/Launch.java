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

package net.openbyte;

import net.openbyte.data.Files;
import net.openbyte.data.file.OpenProjectSolution;
import net.openbyte.event.handle.EventManager;
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

    /**
     * The SLF4J logger to the console.
     */
    public static final Logger logger = LoggerFactory.getLogger("OpenByte");

    /**
     * The list of project names current loaded into runtime
     */
    public static final ArrayList<String> projectNames = new ArrayList<String>();

    /**
     * A map to convert names to solutions.
     */
    public static final HashMap<String, OpenProjectSolution> nameToSolution = new HashMap<String, OpenProjectSolution>();

    /**
     * A double that updates every release and contains the current version.
     */
    public static final double CURRENT_VERSION = 1.0;

    /**
     * This is the main method that allows Java to initiate the program.
     *
     * @param args the arguments to the Java program, which are ignored
     */
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
        logger.info("Starting event manager...");
        EventManager.init();
        logger.info("Detecting plugin files...");
        File[] pluginFiles = PluginManager.getPluginFiles(Files.PLUGINS_DIRECTORY);
        logger.info("Detected " + pluginFiles.length + " plugin files in the plugins directory!");
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
