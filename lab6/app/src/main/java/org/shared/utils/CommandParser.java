package org.shared.utils;

import org.shared.datatransferobjects.Request;

/*
 * command handler
*/
public class CommandParser {
    /*
     * @param line - the command line
     */
    public static Request parse(String line) {

        String[] splitedLine = line.split(" ", 2);
        if (splitedLine.length < 2) {
            return new Request(splitedLine[0], null);
        }
    
        return new Request(splitedLine[0], splitedLine[1]);
    }
}
