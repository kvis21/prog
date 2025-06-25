package org.server.filehandlers;

import java.io.FileNotFoundException;

public interface Readable<T> {
    T read() throws FileNotFoundException;
}
