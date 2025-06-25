package org.utils;

/*
 * command handler
*/
public class CommandParser {
    /*
     * @param line - the command line
     */
    public static String[] parse(String line) {
        String[] args = new String[2];
        String[] splitedLine = line.split(" ", 2);
    
        for (int i = 0; i < splitedLine.length; i++) {
            args[i] = splitedLine[i];
        }
        return args;
    }
}
