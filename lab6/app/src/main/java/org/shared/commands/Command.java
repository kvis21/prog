package org.shared.commands;

import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;

public abstract class Command {
    protected final String name;

    public Command(String name) {
        this.name = name;
    }
    
    abstract public Response execute(Request request);

    abstract public String getDescription();

    public String getName() {
        return name;
    }
}
