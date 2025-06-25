package org.commands;

public abstract class Command {
    protected final String name;

    public Command(String name) {
        this.name = name;
    }
    
    abstract public void execute(String args);

    abstract public String getDescription();

    public String getName() {
        return name;
    }
}
