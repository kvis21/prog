package org.client.console;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.shared.exceptions.EndOfFileException;

/**
 * Default console implementation for input/output operations.
 * Supports reading from both standard input and files.
 */
public class DefaultConsole implements Console {
    private static DefaultConsole instance = new DefaultConsole();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    /**
     * Returns the singleton console instance.
     *
     * @return DefaultConsole instance
     */
    static public DefaultConsole getConsole() {
        return instance;
    }

    /**
     * Prints text to standard output.
     *
     * @param obj the string to print
     */
    @Override
    public void print(String obj) {
        System.out.print(obj);
    }

    /**
     * Prints text to standard output with newline.
     *
     * @param obj the string to print
     */
    @Override
    public void println(String obj) {
        System.out.println(obj);
    }

    /**
     * Prints error message to standard error.
     *
     * @param errorMessage the error message to display
     */
    @Override
    public void printError(String errorMessage) {
        System.err.println(errorMessage);
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader newReader) {
        this.reader = newReader;
    }

    /**
     * Reads a line from current input channel.
     *
     * @return the read line
     * @throws IOException        if an I/O error occurs
     * @throws EndOfFileException when end of file reached
     */
    @Override
    public String readline() throws IOException, EndOfFileException {
        String line = reader.readLine();
        if (line == null) {
            throw new EndOfFileException();
        }
        return line;
    }

    /**
     * Switches input channel to read from specified file.
     *
     * @param scriptPath path to the input file
     * @throws IOException if file cannot be opened
     */
    public void setChannelToReader(String scriptPath) {
        try {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if (scriptPath == null) {
                reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(scriptPath), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        }
    }

    public boolean ready() {
        try {
            return getReader().ready();
        } catch (IOException e) {
            return false;
        }
    }
}