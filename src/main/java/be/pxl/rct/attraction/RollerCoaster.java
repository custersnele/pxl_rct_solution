package be.pxl.rct.attraction;

import be.pxl.rct.visitor.Visitor;

import javax.swing.plaf.InternalFrameUI;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

public class RollerCoaster extends Thread implements Comparable<RollerCoaster> {

    private String name;
    private AttractionType attractionType;
    private double price;
    private long closingTime;

    private Queue<Visitor> waitingLine;

    public RollerCoaster(String name, AttractionType attractionType) {
       this(name, attractionType, 0);
    }

    public RollerCoaster(String name, AttractionType attractionType, double price) {
        this.name = name;
        this.attractionType = attractionType;
        waitingLine = new LinkedList<>();
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public synchronized void enterWaitingLine(Visitor visitor) {

            waitingLine.add(visitor);
            visitor.pay(price);
            visitor.setInWaitingLine(true);

    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < closingTime) {
            if (!waitingLine.isEmpty()) {
                for (int i = 0; i < attractionType.getPassengers(); i++) {
                        Visitor visitor = waitingLine.poll();
                        if (visitor != null) {
                            visitor.takeRide(this);
                        }
                }
            }
        }
    }

    public synchronized int getWaitingLineSize() {
        return waitingLine.size();
    }

    public AttractionType getAttractionType() {
        return attractionType;
    }

    public void showDetails() {
        System.out.println("*** " + name);
        System.out.println("  --> " + getWaitingLineSize());
    }

    @Override
    public int compareTo(RollerCoaster rollerCoaster) {
        return String.CASE_INSENSITIVE_ORDER.compare(name, rollerCoaster.name);
    }

    public void setClosingTime(long closingTime) {
        this.closingTime = closingTime;
    }
}
