package be.pxl.rct.attraction;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Specification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final double value;
    private final Rating rating;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specification that = (Specification) o;
        return Double.compare(that.value, value) == 0 && rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, rating);
    }
}
