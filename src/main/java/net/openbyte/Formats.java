package net.openbyte;

/**
 * Represents class formats
 */
public class Formats {

    /**
     * Represents the class format
     * @param thePackage the package the class is in
     * @param className the class name
     * @return the format
     */
    public static String classFormat(String thePackage, String className){
        String format = "package " + thePackage + ";" + " \n" + "public class " + className + " {" + " \n" + "}";
        return format;
    }
}
