package org.client;

public final class Configuration {
    /*
     * path of the directory where data is stored
     */
    public static final String DATA_PATH = System.getenv("DATA_FILE_PATH");

    /*
    * path of the file where the collection is stored
    */
    public static final String COLLECTION_FILE_PATH = DATA_PATH+"/collection.json";

    /*
    * path of the backup file
    */
    public static final String BACKUP_FILE_PATH = DATA_PATH+"/backup.json";

}
