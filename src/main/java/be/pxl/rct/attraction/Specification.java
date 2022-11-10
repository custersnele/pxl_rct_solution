package be.pxl.rct.attraction;

public class Specification {
    private double value;
    private Rating rating;

    public Specification(double value, Rating rating) {
        this.value = value;
        this.rating = rating;
    }

    public double getValue() {
        return value;
    }

    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return rating + "(" + value + ")";
    }
}
