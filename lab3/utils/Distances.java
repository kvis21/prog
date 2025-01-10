package utils;

public enum Distances {
    LENGTH("длинная"),
    SHORT("короткая"),
    SIXMILES("6 миль");

    String distance;

    Distances(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return this.distance;
    }
}
