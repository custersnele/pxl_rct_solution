package be.pxl.rct.attraction;

public class AttractionType {
    private int id;
    private String type;
    private AttractionGenre genre;
    private Specification excitement;
    private Specification nausea;
    private double cost;
    private int passengers;

    private int runningTime;

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

    public AttractionGenre getGenre() {
        return genre;
    }

    public void setGenre(AttractionGenre genre) {
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

    public String stringFewDetails() {
        return "[" + id + "] " + type + " " + genre + " " + cost;
    }

    public String stringAllDetails() {
        return "[" + id + "] " + type + " " + genre +  " E: " + excitement + " N: " + nausea + " " +  cost;
    }
}
