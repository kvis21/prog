package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.exceptions.NotFoundException;
import org.shared.models.Flat;

public class RemoveById extends Command {
    public RemoveById() {
        super("removeById");
    }

    public Response execute(Request request) {
        try {
            Flat flat = CollectionManager.getInstance().getById(Integer.parseInt(request.getArgs()));
            if (flat.getUserId() != request.getUser().userId()) {
                return new Response("Не удалось удалить элемент по указанному id: элемент принадлежит другому пользователю");
            }
            CollectionManager.getInstance().remove(flat);
            return new Response("Удален элемент с id: " + request.getArgs());
        } catch (NotFoundException e) {
            return new Response("Не удалось удалить элемент по указанному id: элемент не найден");
        }
    }

    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
