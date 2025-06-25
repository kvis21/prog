package org.filehandlers;

import java.io.FileNotFoundException;

public interface Readable <T> {
    T read() throws FileNotFoundException;
}
