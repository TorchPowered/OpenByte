package net.openbyte.data.file;

import net.openbyte.data.DataStore;

import java.io.File;

/**
 * Represents a project solution file.
 */
public class OpenProjectSolution {
    private DataStore solution;

    private OpenProjectSolution(File file){
        this.solution = new DataStore(file);
        try {
            solution.loadDataStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OpenProjectSolution getProjectSolutionFromFile(File file){
        return new OpenProjectSolution(file);
    }

    public void setProjectName(String name){
        solution.set("projectName", name);
        try {
            solution.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProjectName(){
        return solution.getString("projectName");
    }

    public File getProjectFolder(){
        return (File) solution.get("projectFolder");
    }

    public void setProjectFolder(File file){
        solution.set("projectFolder", file);
        try {
            solution.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
