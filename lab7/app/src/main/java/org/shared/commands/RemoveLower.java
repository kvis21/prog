package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.models.Flat;
import org.server.modelcreaters.FlatCreater;

public class RemoveLower extends Command implements Createable {

    public RemoveLower() {
        super("removeLower");
    }

    public Response execute(Request request) {
        try {
            Flat flat = request.getObject();

            CollectionManager.getInstance()
                    .removeIf(
                            flat1 -> flat1.compareTo(flat) < 0 &&
                                    flat1.getUserId() == request.getUser().userId()
                    );

            return new Response("Элементы удалены");
        } catch (Exception e) {
            return new Response("Не удалось удалить элементы коллекции" + e.getMessage());
        }
    }

    public Flat create() {
        return new FlatCreater().build();
    }

    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }

}
