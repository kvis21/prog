package org.shared.dto;

import java.io.Serializable;

import org.shared.models.Flat;

public class Request implements Serializable {

    private Flat flat;
    private String commandName;
    private String args;
    private UserDTO user;

    public Request(UserDTO user, String commandName, String args, Flat flat) {
        this.user = user;
        this.flat = flat;
        this.args = args;
        this.commandName = commandName;
    }


    public Request(UserDTO user, String commandName, String args) {
        this.user = user;
        this.flat = null;
        this.args = args;
        this.commandName = commandName;
    }

    public Request(String commandName, String args) {
        this.commandName = commandName;
        this.args = args;
    }
    public Request(String commandName) {}
    
    public UserDTO getUser(){
        return user;
    }

    public Flat getObject() {
        return flat;
    }

    public String getCommandName() {
        return commandName.trim().toLowerCase();
    }

    public String getArgs() {
        return args;
    }

}