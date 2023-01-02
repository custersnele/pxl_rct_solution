package be.pxl.rct.attraction;

import java.io.Serializable;

public class RollercoasterType implements Serializable {
    private int id;
    private String type;
    private RideGenre genre;
    private Specification excitement;
    private Specification nausea;
    private double cost;
    private int passengers;

    private int runningTime;

    public RollercoasterType(int id, RideGenre genre, double cost) {
        this.id = id;
        this.genre = genre;
        this.cost = cost;
    }

    public RollercoasterType(int id, String type, RideGenre genre, double cost) {
        this.id = id;
        this.type = type;
        this.genre = genre;
        this.cost = cost;
    }

    public RollercoasterType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RideGenre getGenre() {
        return genre;
    }

    public void setGenre(RideGenre genre) {
        this.genre = genre;
    }

    public Specification getExcitement() {
        return excitement;
    }

    public void setExcitement(Specification excitement) {
        this.excitement = excitement;
    }

    public Specification getNausea() {
        return nausea;
    }

    public void setNausea(Specification nausea) {
        this.nausea = nausea;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public int getRunningTime() {
        return runningTime;
    }

    @Override
    public String toString() {
        return "AttractionType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", genre=" + genre +
                ", excitement=" + excitement +
                ", nausea=" + nausea +
                ", cost=" + cost +
                ", passengers=" + passengers +
                '}';
    }

    public String getBasicDetails() {
        return type + ", " + genre;
    }

    public String getDetails() {
        return "[" + id + "] " + type + " " + genre + " " + cost;
    }

    public String getAllDetails() {
        return "[" + id + "] " + type + " " + genre +  " E: " + excitement + " N: " + nausea + " " +  cost;
    }

}
