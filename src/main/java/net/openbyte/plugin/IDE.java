package net.openbyte.plugin;

/**
 * Represents the IDE in the API.
 *
 * @since API Version 1.0 final
 */
public class IDE {

    // No inits for this class
    private IDE(){}

    /**
     * Retrieves a IDE class
     *
     * @param className the IDE class name
     * @return the IDE Class
     * @throws IllegalAccessException if it is not a IDE class
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
