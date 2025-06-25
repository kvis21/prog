package org.server.modelcreaters;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import org.client.console.DefaultConsole;

/**
 * The {@code ModelCreater} class is an abstract base class for creating models of type {@code T}.
 **/
public abstract class ModelCreater<T> {

    /**
     * Builds and returns an instance of the model. This method must be implemented by subclasses
     * to define the logic for creating the model.
     *
     * @return An instance of the model.
     */
    public abstract T build();


    /**
     * Prompts the user for input, parses it, and validates it using the provided predicate and parser.
     * This method continues to prompt the user until valid input is provided.
     *
     * @param <K>       The type of the parsed input.
     * @param prompt    The prompt message displayed to the user.
     * @param validator A predicate to validate the parsed input.
     * @param parser    A function to parse the input string into the desired type.
     * @return The parsed and validated input.
     */
    protected <K> K InputParser(String prompt, Predicate<K> validator, Function<String, K> parser) {
        while (true) {
            try {
                DefaultConsole.getConsole().print(prompt);
                K value = parser.apply(DefaultConsole.getConsole().readline());
                if (validator.test(value)) {
                    return value;
                }
            } catch (NumberFormatException | IOException e) {
                DefaultConsole.getConsole().printError("Ошибка: неправильный формат ввода");
            }
        }
    }

    protected <K> K EnumParser(String prompt, Predicate<String> validator, Function<String, K> parser) {
        while (true) {
            try {
                DefaultConsole.getConsole().print(prompt);
                String value = DefaultConsole.getConsole().readline();
                if (validator.test(value)) {
                    return parser.apply(value);
                }
            } catch (NumberFormatException | IOException e) {
                DefaultConsole.getConsole().printError("Ошибка: неправильный формат ввода");
            }
        }
    }

    /**
     * Prompts the user for boolean input, validates it using the provided predicate, and parses it
     * into a {@code Boolean} value. This method continues to prompt the user until valid input is provided.
     *
     * @param prompt    The prompt message displayed to the user.
     * @param validator A predicate to validate the input string.
     * @param parser    A function to parse the input string into a {@code Boolean} value.
     * @return The parsed and validated boolean input.
     */
    protected Boolean BooleanParser(String prompt, Predicate<String> validator, Function<String, Boolean> parser) {
        while (true) {
            try {
                DefaultConsole.getConsole().print(prompt);
                String line = DefaultConsole.getConsole().readline();
                if (validator.test(line)) {
                    Boolean value = parser.apply(line);
                    return value;
                }
            } catch (NumberFormatException | IOException e) {
                DefaultConsole.getConsole().printError("Ошибка: неправильный формат ввода");
            }
        }
    }

}
