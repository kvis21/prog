package org.shared.datatransferobjects;

import java.io.Serializable;

import org.shared.models.Flat;

public class Request implements Serializable {

    private final Flat flat;
    private final String commandName;
    private final String args;

    public Request(String commandName, String args, Flat flat) {
        this.flat = flat;
        this.args = args;
        this.commandName = commandName;
    }

    public Request(String commandName, String args) {
        this.flat = null;
        this.args = args;
        this.commandName = commandName;
    }
    
    public Flat getObject() {
        return flat;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

}