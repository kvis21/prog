package org.shared.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.shared.models.Flat;

public class Response implements Serializable {
    private final String message;
    private final Flat data;
    private UserDTO user;
    private boolean isExist;

    public Response(String message) {
        this.message = message;
        this.data = null;
    }

    public Response(String message, Flat data) {
        this.message = message;
        this.data = data;
    }

    public Response(UserDTO user) {
        this.user = user;
        this.message = null;
        this.data = null;
    }

    public Response(boolean isExist) {
        this.isExist = isExist;
        this.message = null;
        this.data = null;
    }

    public boolean isExist() {
        return isExist;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        return baos.toByteArray();
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public UserDTO getUser() {
        return user;
    }
}