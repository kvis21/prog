package org.shared.datatransferobjects;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.shared.models.Flat;

public class Response implements Serializable {
    private final String message;
    private final Flat data;

    public Response(String message) {
        this.message = message;
        this.data = null;
    }

    public Response(String message, Flat data) {
        this.message = message;
        this.data = data;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        return baos.toByteArray();
    }

    public String getMessage() { return message; }
    public Object getData() { return data; }
}