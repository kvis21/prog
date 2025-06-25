package org.server.database.security;

import org.client.UDPClient;
import org.shared.dto.Request;
import org.shared.dto.Response;

public class AuthUser {

    private UDPClient client;

    public AuthUser(UDPClient client) {
        this.client = client;
    }

    public Response checkUser(String login) throws ClassNotFoundException {
        Request request = new Request("checkUser", login);
        return client.sendRequestWithRetry(request);
    }

    public Response register(String login, String password) throws ClassNotFoundException{
        Request request = new Request("register", login+" "+password);
        return client.sendRequestWithRetry(request);
    }

    public Response login(String login, String password) throws ClassNotFoundException{
        Request request = new Request("login", login+" "+password);
        return client.sendRequestWithRetry(request);
    }


}