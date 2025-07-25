package org.server.modelcreaters;

import org.server.collections.CollectionManager;
import org.shared.models.Flat;
import org.shared.models.View;

import java.time.LocalDateTime;

public class FlatCreater extends ModelCreater<Flat>{

    public void update(){

    }

    public Flat build(){
        Flat flat = new Flat();

        setId(flat);
        setName(flat);
        setCoordinate(flat);
        setCreationTime(flat);
        setArea(flat);
        setNumberOfRooms(flat);
        setBalcony(flat);
        setNumberOfBathrooms(flat);
        setView(flat);
        setHouse(flat);

        return flat;
    }

    private void setId(Flat flat){
        flat.setId(CollectionManager.getInstance().generateId());
    }

    private void setName(Flat flat){
        String name = InputParser(
            "Введите название квартиры (название не может быть пустой строкой): ",
            value -> value!=null,
            String::valueOf
        );
        flat.setName(name);

    }

    private void setCoordinate(Flat flat){
        flat.setCoordinates(new CreateCoordinates().build());
    }

    private void setCreationTime(Flat flat){
        flat.setCreationDate(LocalDateTime.now());
    }

    private void setArea(Flat flat){
        float area = InputParser(
            "Введите площадь квартиры (значение должно быть > 0): ",
            value -> value>0,
            Float::valueOf
        );
        flat.setArea(area);
    }

    private void setNumberOfRooms(Flat flat){
        flat.setNumberOfRooms(InputParser(
            "Введите количество комнат (значение должно быть > 0): ",
            value -> value>0,
            Integer::valueOf
        ));
    }

    private void setBalcony(Flat flat){
        flat.setBalcony(BooleanParser(
            "Введите true, если балкон есть, иначе false: ",
            value -> value.toLowerCase().equals("true") || value.toLowerCase().equals("false"),
            Boolean::valueOf
        ));
    }

    private void setNumberOfBathrooms(Flat flat){
        flat.setNumberOfBathrooms(InputParser(
            "Введите количество ванных (значение должно быть > 0): ",
            value -> value > 0,
            Integer::valueOf
        ));
    }
    private void setView(Flat flat){
        flat.setView(InputParser(
            "Введите вид из квартиры (YARD, TERRIBLE, PARK): ",
            value -> value.toString()=="YARD" || value.toString()=="TERRIBLE" || value.toString()=="PARK",
            value -> View.valueOf(value.toUpperCase())
        ));   
    }

    private void setHouse(Flat flat){
        flat.setHouse(new CreateHouse().build());
    }

}
