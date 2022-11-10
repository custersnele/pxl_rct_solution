package be.pxl.rct.attraction;

import be.pxl.rct.visitor.Visitor;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

public class RollerCoaster extends Thread {

    private String name;
    private AttractionType attractionType;
    private double price;

    private Queue<Visitor> waitingLine;

    public RollerCoaster(String name, AttractionType attractionType) {
       this(name, attractionType, 0);
    }

    public RollerCoaster(String name, AttractionType attractionType, double price) {
        this.name = name;
        this.attractionType = attractionType;
        waitingLine = new SynchronousQueue<>();
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public synchronized void enterWaitingLine(Visitor visitor) {
        visitor.pay(price);
        waitingLine.add(visitor);
        visitor.setInWaitingLine(true);
    }

    @Override
    public void run() {
        while (true) {
            if (!waitingLine.isEmpty()) {
                for (int i = 0; i < attractionType.getPassengers(); i++) {
                    Visitor visitor = waitingLine.remove();
                    visitor.takeRide(this);
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
}
