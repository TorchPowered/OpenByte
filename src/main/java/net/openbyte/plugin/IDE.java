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
