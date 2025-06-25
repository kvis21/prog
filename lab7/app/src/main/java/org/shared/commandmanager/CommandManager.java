package org.shared.commandmanager;

import java.util.HashMap;
import java.util.Map;

import org.shared.commands.Add;
import org.shared.commands.AddIfMin;
import org.shared.commands.AverageNumberOfBathroom;
import org.shared.commands.Clear;
import org.shared.commands.Command;
import org.shared.commands.Exit;
import org.shared.commands.FilterContainsName;
import org.shared.commands.Help;
import org.shared.commands.Info;
import org.shared.commands.MinByNumberOfRooms;
import org.shared.commands.RemoveById;
import org.shared.commands.RemoveGreater;
import org.shared.commands.RemoveLower;
import org.shared.commands.ScriptExecute;
import org.shared.commands.Show;
import org.shared.commands.Update;
import org.shared.dto.Request;
import org.shared.dto.Response;

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
    static {
        register("help", new Help());
        register("info", new Info());
        register("show", new Show());
        register("add", new Add());
        register("update", new Update());
        register("removeById", new RemoveById());
        register("clear", new Clear());
        register("executeScript", new ScriptExecute());
        register("exit", new Exit());
        register("addIfMin", new AddIfMin());
        register("removeGreater", new RemoveGreater());
        register("removeLower", new RemoveLower());
        register("AverageNumberOfBathrooms", new AverageNumberOfBathroom());
        register("minByNumberOfRooms", new MinByNumberOfRooms());
        register("filterContainsName", new FilterContainsName());
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
    public static void register(String commandName, Command command) {
        commands.put(commandName.toLowerCase(), command);
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
    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    /**
     * Executes a command by name with the provided arguments.
     * @param request request object containing command name and arguments
     * @return Response with execution result
     */
    public static Response executeCommand(Request request) {
        String commandName = request.getCommandName();
        
        Command command = getCommand(commandName);
        if (command == null) {
            return new Response("Команда '" + commandName + "' не найдена. Введите 'help' для списка команд.");
        }

        try {
            return command.execute(request);
            
        } catch (Exception e) {
            return new Response("Ошибка выполнения команды '" + commandName + "': " + e.getMessage());
        }
    }
}