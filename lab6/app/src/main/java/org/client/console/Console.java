package org.client.console;

import java.io.IOException;


/**
 * Консоль для ввода команд и вывода результата
 */
public interface Console {
    
    void print(String obj);

    void println(String obj);

    void printError(String errorMessage);

    String readline() throws IOException;

}