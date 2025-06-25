package org.models.modelcreaters;
import org.models.Coordinates;

public class CreateCoordinates extends ModelCreater<Coordinates>{

    public Coordinates build(){
        Coordinates coordinates = new Coordinates();
        
        int x = InputParser(
            "Введите значение x (должно быть > -846): ",
            value -> value > -846,
            Integer::valueOf
        );
        coordinates.setX(x);

        int y = InputParser(
            "Введите значение y (должно быть <= 321): ",
            value -> value <= 321,
            Integer::valueOf
        );
        coordinates.setY(y);

        return coordinates;
        
    }
}
