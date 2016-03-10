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

package net.openbyte.data.file;

import net.openbyte.enums.ModificationAPI;

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
    /**
     * A properties representation of the file.
     */
    private Properties solution;
    /**
     * The file which is formatted as a OpenProjectSolution
     */
    private File saveToFile;

    /**
     * Creates a new open project solution from a specified file
     *
     * @param file the file that the openprojectsolution format will be read from
     */
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

    /**
     * Saves the changes made to the file that the format read from
     */
    private void save(){
        try {
            solution.store(new FileOutputStream(saveToFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the project solution from the specified file.
     *
     * @param file the file where the solution is formatted
     * @return the decoded solution object
     */
    public static OpenProjectSolution getProjectSolutionFromFile(File file){
        return new OpenProjectSolution(file);
    }

    /**
     * Deletes the solution.
     */
    public void deleteSolution(){
        System.gc();
        saveToFile.delete();
    }

    /**
     * Sets the project name.
     *
     * @param name the project name
     */
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

    public void setModificationAPI(ModificationAPI api) {
        if(api == ModificationAPI.MCP) {
            solution.setProperty("api", "mcp");
            save();
            return;
        }
        solution.setProperty("api", "forge");
        save();
    }

    public ModificationAPI getModificationAPI() {
        String api = solution.getProperty("api", "forge");
        if(api.equals("forge")) {
            return ModificationAPI.MINECRAFT_FORGE;
        }
        return ModificationAPI.MCP;
    }
}
