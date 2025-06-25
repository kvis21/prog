package org.server.database.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    static public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");

            byte[] messageDigest = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String inputPassword, String hashedPassword) {
        if (inputPassword == null || hashedPassword == null) {
            return false;
        }

        String inputHashed = hashPassword(inputPassword);
        return inputHashed.equals(hashedPassword);
    }

}
