package org.utils.backup;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.Configuration;
import org.collections.CollectionManager;

import org.console.DefaultConsole;
import org.filehandlers.JSONHandler;

import com.google.gson.reflect.TypeToken;

/*
 * The {@code Backup} class is responsible for managing backup operations for the application's data. 
*/
public class Backup implements Loadable, Saveable{
    /**
     * A {@link JSONHandler} instance used to read and write backup data in JSON format.
     * The handler is configured to work with {@link CollectionManager} objects and uses
     * the backup file path specified in {@link Configuration#BACKUP_FILE_PATH}.
     */
    private JSONHandler<CollectionManager> jsonHandler = new JSONHandler<CollectionManager>(new TypeToken<CollectionManager>(){}, Configuration.BACKUP_FILE_PATH);

    /*
     * Loads the backup data
    */
    @Override
    public boolean load(){
        try{
            if (!checkEqualsCollections() && backupExists()){
                DefaultConsole.getConsole().print("Загрузить последние изменения?(yes, no)");
                while (true){
                    String answer = DefaultConsole.getConsole().readline();
                    if (answer.equals("yes")){
                        CollectionManager.setInstance(jsonHandler.read());
                        return true;
                    }
                    else if(answer.equals("no")){
                        jsonHandler.removeFile();
                        return false;
                    }
                }
            } 
        } catch (FileNotFoundException e){
        }catch(IOException e){
            DefaultConsole.getConsole().print("Не удалось загрузить последние изменения");
        }
        return false;
    }

    private Boolean checkEqualsCollections() throws IOException{
        try{

            return jsonHandler.read().getCollection().equals(CollectionManager.getInstance()
                                                                        .getJsonHandler()
                                                                        .read()
                                                                        .getCollection());
            
        } catch (FileNotFoundException|NullPointerException e){
            return false;
        } 
    }

    /*
     * Saves the current data to the backup
    */
    @Override
    public void save() {
        jsonHandler.write(CollectionManager.getInstance());
    }

    /*
     * Checks if the backup file exists
    */
    private Boolean backupExists(){
        return jsonHandler.checkFilesExistence();
    }


}
