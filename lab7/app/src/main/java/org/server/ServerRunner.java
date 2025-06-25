package org.server;

import java.io.IOException;
import org.shared.configs.ServerConfiguration;
import org.server.network.UDPServer;

public class ServerRunner {
    public static void main(String[] args) {
        try (UDPServer server = new UDPServer(ServerConfiguration.PORT)) {
            server.loadCollection();
            server.start();
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
        }
    }
}
