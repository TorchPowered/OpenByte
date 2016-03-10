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

/**
 * Represents class formats used for creating a new file.
 */
public class Formats {

    /**
     * Represents the regular class creation format.
     *
     * @param thePackage the package the class is in
     * @param className the class name
     * @return the format
     */
    public static String classFormat(String thePackage, String className){
        String format = "package " + thePackage + ";" + " \n" + "public class " + className + " {" + " \n" + "}";
        return format;
    }

    /**
     * Represents the java interface class format.
     *
     * @param thePackage the package the interface is in
     * @param interfaceName the interface name
     * @return the format
     */
    public static String interfaceFormat(String thePackage, String interfaceName) {
        String format = "package " + thePackage + ";" + "\n" + "public interface " + interfaceName + " {" + "\n" + "}";
        return format;
    }
}
