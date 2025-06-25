package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;

public class Info extends Command {

    public Info() {
        super("info");
    }

    @Override
    public Response execute(Request request) {
        StringBuilder info = new StringBuilder();
        info.append("Сведения о коллекции:\n");
        info.append(" Тип: " + CollectionManager.getInstance().getCollectionType() + "\n");
        info.append(" Количество элементов: " + CollectionManager.getInstance().getColectionSize() + "\n");
        info.append(" Дата последнего сохранения: " + CollectionManager.getInstance().getLastSaveTime() + "\n");
        info.append(" Дата последней инициализации: " + CollectionManager.getInstance().getLastInitTime() + "\n");
        return new Response(info.toString());
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }

}
