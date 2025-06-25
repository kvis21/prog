package org.filehandlers;

/**
 * An abstract base class for file handlers that provides common functionality
 * for reading and writing objects of type T.
 *
 * @param <T> the type of objects this handler can read and write
 * @see Readable
 * @see Writable
 */
public abstract class Handler<T> implements Readable<T>, Writable<T>{
    
}
