package org.shared.models;

import java.io.Serializable;

public class House implements Serializable{
    private String name; //Поле не может быть null
    private Long year; //Значение поля должно быть больше 0
    private long numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    
    public House(){    
    }

    public House(String name, Long year, long numberOfFlatsOnFloor) {
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public void setNumberOfFlatsOnFloor(long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public String getName() {
        return name;
    }

    public Long getYear() {
        return year;
    }

    public long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }


    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFlatsOnFloor=" + numberOfFlatsOnFloor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return name.equals(house.name) && year.equals(house.year) && numberOfFlatsOnFloor == house.numberOfFlatsOnFloor;   
    }
}
