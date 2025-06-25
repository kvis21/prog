package org.shared.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a Flat entity with various properties and constraints.
 * Implements Comparable for comparing flats by area.
 */


public class Flat implements Serializable {
    int userId;
    /**
     * Non-null, positive, unique, auto-generated ID
     */
    private Integer id;
    /**
     * Non-null, non-empty string
     */
    private String name;
    /**
     * Non-null coordinates
     */
    private Coordinates coordinates;
    /**
     * Non-null, auto-generated creation date
     */
    private LocalDateTime creationDate;
    /**
     * Area value (must be > 0)
     */
    private float area;
    /**
     * Number of rooms (must be > 0)
     */
    private Integer numberOfRooms;
    /**
     * Balcony availability flag
     */
    private boolean balcony;
    /**
     * Number of bathrooms (must be > 0)
     */
    private int numberOfBathrooms;
    /**
     * Non-null view type
     */
    private View view;
    /**
     * Optional house reference (can be null)
     */
    private House house;

    public Flat() {
    }

    public Flat(Integer id,
                int userId,
                String name,
                Coordinates coordinates,
                LocalDateTime creationDate,
                float area,
                Integer numberOfRooms,
                boolean balcony,
                int numberOfBathrooms,
                View view,
                House house) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.balcony = balcony;
        this.numberOfBathrooms = numberOfBathrooms;
        this.view = view;
        this.house = house;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the flat's ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the flat's ID.
     *
     * @param id must be non-null, positive and unique
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the flat's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the flat's name.
     *
     * @param name must be non-null and non-empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the flat's coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the flat's coordinates.
     *
     * @param coordinates must be non-null
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the creation date
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     *
     * @param creationDate must be non-null
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    /**
     * Sets balcony availability.
     *
     * @param balcony true if flat has balcony
     */
    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    // Getters with return value documentation

    /**
     * @return the view type
     */
    public View getView() {
        return view;
    }

    /**
     * Sets the view type.
     *
     * @param view must be non-null
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * @return the house reference (may be null)
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house reference.
     *
     * @param house can be null
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * @return the flat's area
     */
    public float getArea() {
        return area;
    }

    /**
     * Sets the flat's area.
     *
     * @param area must be greater than 0
     */
    public void setArea(float area) {
        this.area = area;
    }

    /**
     * @return number of bathrooms
     */
    public Integer getNumberOfBathrooms() {
        return numberOfRooms;
    }

    /**
     * Sets number of bathrooms.
     *
     * @param numberOfBathrooms must be greater than 0
     */
    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    /**
     * @return number of rooms
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets number of rooms.
     *
     * @param numberOfRooms must be greater than 0
     */
    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Compares flats by area.
     *
     * @param flat the flat to compare to
     * @return comparison result
     */
    public int compareTo(Flat flat) {
        return ((Float) (this.getArea() * this.getNumberOfRooms())).compareTo(flat.getArea() * flat.getNumberOfRooms());
    }

    /**
     * @return string representation of the flat
     */
    @Override
    public String toString() {
        return "Flat{\n" +
                " id=" + id +
                ",\n name='" + name + '\'' +
                ",\n coordinates=" + coordinates +
                ",\n creationDate=" + creationDate +
                ",\n area=" + area +
                ",\n numberOfRooms=" + numberOfRooms +
                ",\n balcony=" + balcony +
                ",\n numberOfBathrooms=" + numberOfBathrooms +
                ",\n view=" + view +
                ",\n house=" + house +
                "\n}";
    }

    /**
     * Checks equality based on all fields except view and house.
     *
     * @param o object to compare
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return id.equals(flat.id) &&
                flat.area == area &&
                numberOfRooms.equals(flat.numberOfRooms) &&
                balcony == flat.balcony &&
                numberOfBathrooms == flat.numberOfBathrooms &&
                name.equals(flat.name) &&
                coordinates.equals(flat.coordinates) &&
                creationDate.equals(flat.creationDate);
    }

    /**
     * Generates hash code based on all fields except view and house.
     *
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id,
                area,
                numberOfRooms,
                balcony,
                numberOfBathrooms,
                name,
                coordinates,
                creationDate);
    }
}