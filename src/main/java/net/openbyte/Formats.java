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

    /**
     * Represents the interface format
     * @param thePackage the packet the interface is in
     * @param interfaceName the interface name
     * @return the format
     */
    public static String interfaceFormat(String thePackage, String interfaceName) {
        String format = "package " + thePackage + ";" + "\n" + "public interface " + interfaceName + " {" + "\n" + "}";
        return format;
    }
}
