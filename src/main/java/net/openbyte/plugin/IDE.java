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

package net.openbyte.plugin;

/**
 * Representation of the environment inside of the API.
 *
 * @since API Version 1.0 final
 */
public class IDE {

    // Disables allowing to create new instances of the class.
    private IDE(){}

    /**
     * Retrieves a class which is inside of the IDE.
     *
     * @param className the name of the class which you want to get
     * @return the class specified inside of the IDE
     * @throws IllegalAccessException if the class specified is not a class inside of the IDE
     */
    public static Class getIDEClass(String className) throws IllegalAccessException {
        if(!className.startsWith("net.openbyte.")) {
            throw new IllegalAccessException();
        }
        try {
            Class theClass = Class.forName(className);
            return theClass;
        } catch (Exception e) {
            System.out.println("Class " + className + " could not be found!");
            e.printStackTrace();
            return null;
        }
    }
}
