package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;


public class AverageNumberOfBathroom extends Command {
    public AverageNumberOfBathroom() {
        super("AverageNumberOfBathrooms");
    }

    public Response execute(Request request) {
        try {

            Float AverageNumberOfBathroom = CollectionManager.getInstance().getAverageNumberOfBathrooms();
            return new Response("Среднее число ванных комнат в коллекции: " + AverageNumberOfBathroom);
        } catch (Exception e) {
            return new Response("Не удалось выполнить команду AverageNumberOfBathroom");
        }
    }

    public String getDescription() {
        return "вывести среднее значение поля numberOfBathrooms для всех элементов коллекции";
    }


}   
