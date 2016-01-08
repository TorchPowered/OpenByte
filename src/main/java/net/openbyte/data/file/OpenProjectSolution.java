package net.openbyte.data.file;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Represents a project solution file.
 */
public class OpenProjectSolution {
    private Properties solution;
    private File saveToFile;

    private OpenProjectSolution(File file){
        this.solution = new Properties();
        try {
            solution.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(solution.get("version") == null){
            solution.setProperty("version", "1.0");
            try {
                solution.store(new FileOutputStream(file), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            solution.load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveToFile = file;
    }

    private void save(){
        try {
            solution.store(new FileOutputStream(saveToFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OpenProjectSolution getProjectSolutionFromFile(File file){
        return new OpenProjectSolution(file);
    }

    public void deleteSolution(){
        JOptionPane.showMessageDialog(null, "Delete the solution file manually at this location: " + saveToFile.getAbsolutePath() + " after you have closed the application.", "Delete the solution", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setProjectName(String name){
        solution.setProperty("name", name);
        save();
    }

    public String getProjectName(){
        return solution.getProperty("name");
    }

    public File getProjectFolder(){
        return new File(solution.getProperty("folderPath"));
    }

    public void setProjectFolder(File file){
        solution.setProperty("folderPath", file.getAbsolutePath());
    }
}
