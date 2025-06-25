package org.filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A concrete implementation of {@link Handler} for basic file I/O operations.
 * Provides functionality for reading and writing strings to/from text files.
 * 
 * @see Handler
 */
public class FileHandler extends Handler<String> {
    /**
     * The reader instance for file input operations.
     */
    private final BufferedReader fileReader;
    
    /**
     * The writer instance for file output operations.
     */
    private final BufferedWriter fileWriter;

    /**
     * Constructs a new FileHandler for the specified file path.
     * 
     * @param filePath the path to the file to be handled
     * @throws IOException if an I/O error occurs when opening the file
     */
    public FileHandler(String filePath) throws IOException {
        File file = new File(filePath);
        this.fileReader = new BufferedReader(new FileReader(file));
        this.fileWriter = new BufferedWriter(new FileWriter(file));
    }

    /**
     * Writes a string to the file and immediately flushes the buffer.
     * 
     * @param data the string to be written to the file
     */
    @Override
    public void write(String data) {
        try {
            fileWriter.append(data).flush();
        } catch (IOException ignored) {
            // Silently ignore write errors
        }
    }

    /**
     * Reads a line of text from the file.
     * 
     * @return the line that was read, or null if end of stream is reached
     *         or an I/O error occurs
     */
    @Override
    public String read() {
        try {
            return fileReader.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}