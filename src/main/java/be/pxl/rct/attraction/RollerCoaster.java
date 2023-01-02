package be.pxl.rct.attraction;

import be.pxl.rct.visitor.Visitor;

import java.io.Serializable;

public class RollerCoaster implements Comparable<RollerCoaster>, QueueArea<Visitor>, Serializable {

    private final String name;
    private final RollercoasterType attractionType;

    public RollerCoaster(String name, RollercoasterType attractionType) {
        this.name = name;
        this.attractionType = attractionType;
    }

    public String getName() {
        return name;
    }

    public RollercoasterType getAttractionType() {
        return attractionType;
    }

    public void showDetails() {
        System.out.println("*** " + toString());
    }

    @Override
    public int compareTo(RollerCoaster rollerCoaster) {
        return String.CASE_INSENSITIVE_ORDER.compare(name, rollerCoaster.name);
    }

    @Override
    public int getAllowed() {
        return attractionType.getPassengers();
    }

    @Override
    public int getTime() {
        return attractionType.getRunningTime();
    }

    @Override
    public void enter(Visitor guest) {
        guest.takeRide(this);
    }

    @Override
    public String toString() {
        return name + " [" + attractionType.getBasicDetails() + "]";
    }
}
