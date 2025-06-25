package org.models;

import java.util.Objects;

public class Coordinates{
    private int x; //Значение поля должно быть больше -846
    private Integer y; //Максимальное значение поля: 321, Поле не может быть null
    
    public void setX(int x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
    }

    public boolean validate(){
        return x>846 && y<=321 && y!=null;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates other = (Coordinates) o;
        return x == other.x && Objects.equals(y, other.y);   
    }

}
