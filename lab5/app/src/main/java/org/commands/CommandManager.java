package org.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages application commands using the Singleton pattern.
 * Handles command registration, retrieval, and execution tracking.
 */
public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();
    private static CommandManager instance = new CommandManager();

    /**
     * Private constructor initializes all supported commands.
     */
    private CommandManager() {
        this.register("help", new Help());
        this.register("info", new Info());
        this.register("show", new Show());
        this.register("add", new Add());
        this.register("update", new Update());
        this.register("removeById", new RemoveById());
        this.register("clear", new Clear());
        this.register("save", new Save());
        this.register("executeScript", new ScriptExecute());
        this.register("exit", new Exit());
        this.register("addIfMin", new AddIfMin());
        this.register("removeGreater", new RemoveGreater());
        this.register("removeLower", new RemoveLower());
        this.register("AverageNumberOfBathrooms", new AverageNumberOfBathroom());
        this.register("minByNumberOfRooms", new MinByNumberOfRooms());
        this.register("filterContainsName", new FilterContainsName());
    }

    /**
     * Returns the singleton instance of CommandManager.
     * @return CommandManager instance
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    /**
     * Registers a new command with the manager.
     * @param commandName the name to register the command under
     * @param command the Command implementation
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Returns all registered commands.
     * @return Map of command names to Command objects
     */
    public static Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Retrieves a command by name.
     * @param commandName the name of the command to retrieve
     * @return Command implementation or null if not found
     */
    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}