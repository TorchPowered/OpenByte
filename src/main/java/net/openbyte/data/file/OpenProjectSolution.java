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
        if(solution.get("version") == null){
            solution.set("version", 1.0);
            try {
                solution.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        solution.set("name", name);
    }

    public String getProjectName(){
        return solution.getString("name");
    }

    public File getProjectFolder(){
        return (File) solution.get("folder");
    }

    public void setProjectFolder(File file){
        solution.set("folder", file);
    }

    public void saveSolution(){
        try {
            solution.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            solution.loadDataStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
