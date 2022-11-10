package be.pxl.rct.themepark;

import be.pxl.rct.attraction.AttractionType;
import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.exception.UnsufficientCashException;
import be.pxl.rct.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Themepark {

    private String name;
    private double cash;

    private double entreeFee;
    private List<RollerCoaster> rollerCoasters = new ArrayList<>();
    private List<Visitor> visitors = new ArrayList<>();

    public Themepark(String name, double cash) {
        this.name = name;
        this.cash = cash;
    }


    public double getCash() {
        return cash;
    }

    // TODO history of all payments
    public void pay(double amount) {
        cash -= amount;
    }

    public String getName() {
        return name;
    }

    public synchronized void addVisitor(Visitor visitor) {
        // TODO visit time
        visitor.pay(entreeFee);
        visitor.startVisit(this, 1000);
        visitors.add(visitor);
    }

    public void addAttraction(String name, AttractionType attractionType, double attractionFee) {
        if (attractionType.getCost() > getCash()) {
            throw new UnsufficientCashException();
        }
        pay(attractionType.getCost());
        rollerCoasters.add(new RollerCoaster(name, attractionType, attractionFee));
    }

    public synchronized void showVisitorDetails() {
        System.out.println("Number of visitors: " + visitors.size());
        System.out.println("Average happiness: " + visitors.stream().mapToInt(Visitor::getHappinessLevel).average().orElse(0.0));
    }


    public synchronized void removeVisitor(Visitor visitor) {
        visitors.remove(visitor);
    }

    public Optional<RollerCoaster> findRollercoaster(Visitor visitor) {
        return rollerCoasters.stream().filter(r -> r.getPrice() < visitor.getCashAvailable())
                .min((r1, r2) -> Integer.compare(r1.getWaitingLineSize(), r2.getWaitingLineSize()));
    }
}
