package net.openbyte.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a DAT DataStore, user cannot edit
 */
public class DataStore {
    private File file;
    private Map<String, Object> readMap = new HashMap<String, Object>();
    public DataStore(File dataStore){ this.file = dataStore; }

    /**
     * Loads the datastore, do not call when there is no datastore yet
     * @throws Exception if something goes wrong
     */
    public void loadDataStore() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        readMap = (Map<String, Object>) objectInputStream.readObject();
        objectInputStream.close();
    }

    /**
     * Gets the object at that path
     * @param path the key to the object
     * @return the object of the key
     */
    public Object get(String path){
        return readMap.get(path);
    }

    /**
     * Gets the string from the path
     * @param path the key to the string
     * @return the string of the key
     */
    public String getString(String path){
        return (String) readMap.get(path);
    }

    /**
     * Gets the boolean from the path
     * @param path the key to the boolean
     * @return the boolean of the key
     */
    public Boolean getBoolean(String path){
        return (Boolean) readMap.get(path);
    }

    /**
     * Gets the integer from the path
     * @param path the key to the integer
     * @return the integer of the key
     */
    public Integer getInteger(String path){
        return (Integer) readMap.get(path);
    }

    /**
     * Sets a object to the path
     * @param key the key to the object
     * @param object the object of the key
     */
    public void set(String key, Object object){
        readMap.put(key, object);
    }

    /**
     * Saves any changes made to the datastore
     * @throws Exception if something went wrong
     */
    public void save() throws Exception{
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileOutputStream);
        objectoutputstream.writeObject(readMap);
        objectoutputstream.close();
    }
}
