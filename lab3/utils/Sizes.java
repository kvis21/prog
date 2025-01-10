package utils;

public enum Sizes {
    SMALL("маленький"),
    MEDIUM("средний"),
    LARGE("большой");

    private String size;

    Sizes(String size) {
        this.size = size;
    }
    public String getSize() {
        return this.size;
    }

}
