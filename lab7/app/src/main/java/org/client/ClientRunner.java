package org.client;

import java.io.IOException;

import org.shared.configs.ClientConfiguration;
import org.shared.configs.ServerConfiguration;

class ClientRunner {
    public static void main(String[] args) {
        try{
            //UDPClient client = new UDPClient("192.168.10.80", ServerConfiguration.PORT); //helios
            UDPClient client = new UDPClient(ClientConfiguration.HOST, ServerConfiguration.PORT);

            client.startInteractiveMode();
            client.close();
        } catch(IOException e) {}
    }
}