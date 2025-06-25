package org.commands;

import org.utils.backup.Backup;

public class Exit extends Command{
    public Exit(){
        super("exit");
    }
    public void execute(String args){
        new Backup().save();

        System.exit(0);
    }
    
    public String getDescription(){
        return "завершить программу (без сохранения в файл)";
    }

}
