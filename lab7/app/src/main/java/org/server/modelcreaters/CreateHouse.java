package org.server.modelcreaters;


import org.shared.models.House;

public class CreateHouse extends ModelCreater<House>{
    public House build(){
        House house = new House();

        String name = InputParser(
            "Введите название дома (название не может быть пустой строкой)",
            value -> value!=null,
            String::valueOf
        );
        house.setName(name);

        Long year = InputParser(
            "Введите год постройки дома (значение должно быть > 0)",
            value -> value > 0,
            Long::valueOf
        );
        house.setYear(year);

        long numberOfFlatsOnFloor = InputParser(
            "Введите количество квартир в доме (значение должно быть > 0)",
            value -> value > 0,
            Long::valueOf
        );
        house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloor);

        return house;
        
    }

}
