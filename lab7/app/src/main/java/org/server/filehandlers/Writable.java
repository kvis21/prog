package org.server.filehandlers;

public interface Writable<T> {
    void write(T data);
}
