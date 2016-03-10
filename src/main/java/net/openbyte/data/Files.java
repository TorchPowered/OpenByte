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

package net.openbyte.data;

import java.io.File;
import java.io.IOException;

/**
 * Represents important openbyte directories
 */
public class Files {
    /**
     * Represents the current OpenByte workspace directory.
     */
    public static File WORKSPACE_DIRECTORY = new File(System.getProperty("user.home"), ".openbyte");
    /**
     * Represents the plugins directory inside of the workspace directory.
     */
    public static final File PLUGINS_DIRECTORY = new File(WORKSPACE_DIRECTORY, "plugins");

    /**
     * Creates a new file
     * @param fileName the file name
     * @param parentDirectory the parent directory of the file
     * @return the file created
     */
    public static File createNewFile(String fileName, File parentDirectory){
        File file = new File(parentDirectory, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
