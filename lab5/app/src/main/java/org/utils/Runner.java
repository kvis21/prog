package org.utils;

import java.io.IOException;

import org.collections.CollectionManager;
import org.commands.CommandManager;
import org.console.DefaultConsole;
import org.exceptions.EndOfFileException;
import org.exceptions.FileModifiedException;
import org.exceptions.RecursionDetectException;
import org.utils.backup.Backup;

public class Runner {
    /*
     * object for backup
    */
    Backup backup = new Backup();
    
    /**
     * runner of the application
     */
    public void runApplication() {

        if (!backup.load()){
            try{
                CollectionManager.getInstance().load();
            } catch(FileModifiedException e){

                CollectionManager.getInstance().loadModifiedFile();

                DefaultConsole.getConsole().printError("Error: не удалось загрузить коллекцию по причине: файл был изменен");
                try{
                    while(true){
                        DefaultConsole.getConsole().print("Вы уверены, что хотите загрузить измененную версию? (yes/no)");
                        String answer = DefaultConsole.getConsole().readline();
                        if(answer.equalsIgnoreCase("yes")){
                            CollectionManager.getInstance().loadModifiedFile();
                            break;
                        } else if(answer.equalsIgnoreCase("no")){
                            break;
                        }
                    }
                }catch(IOException ex){}
            } 
        }


        while(true){
            try{
                DefaultConsole.getConsole().print("Введите команду: ");
                
                String[] args = CommandParser.parse(DefaultConsole.getConsole().readline());
                String command = args[0];

                CommandManager.getInstance().getCommand(command).execute(args[1]);
                
                backup.save();
            } catch(EndOfFileException e){
            } catch(IOException e){
                DefaultConsole.getConsole().printError("Error: не получилось прочитать  ввод");
            } catch(NullPointerException e){
                DefaultConsole.getConsole().printError("Error: несуществующая команда, воспользуйтесь \"help\" для справки");
            } catch(RecursionDetectException e){
                DefaultConsole.getConsole().printError("Error: замечена рекурсия");
            }
        }
    }
    
}
