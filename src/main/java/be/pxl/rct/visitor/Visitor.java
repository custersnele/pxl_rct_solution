package be.pxl.rct.visitor;

import be.pxl.rct.attraction.WaitingLine;
import be.pxl.rct.exception.UnsufficientCashException;
import be.pxl.rct.shop.ItemType;
import be.pxl.rct.shop.Shop;
import be.pxl.rct.themepark.Themepark;

import java.util.Optional;

public class Visitor extends Thread {
    private int age;
    private String firstname;
    private double cashAvailable;
    private double cashSpent;
    private boolean hungry;
    private boolean thirsty;
    private boolean inWaitingLine;

    private int numberOfRides;
    private int happinessLevel = 100;

    private long threadEndTime;
    private long visitStart;
    private long visitEnd;
    private boolean debug;

    private Themepark themepark;

    public Visitor() {
    }

    public Visitor(String firstname, double cashAvailable) {
        this.firstname = firstname;
        this.cashAvailable = cashAvailable;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    public double getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(double cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public double getCashSpent() {
        return cashSpent;
    }

    public boolean isHungry() {
        return hungry;
    }

    public boolean isThirsty() {
        return thirsty;
    }

    public int getHappinessLevel() {
        return happinessLevel;
    }

    public void updateHappiness(int delta) {
        happinessLevel += delta;
        if (happinessLevel < 0) {
            happinessLevel = 0;
        }
        if (happinessLevel > 100) {
            happinessLevel = 100;
        }
    }

    @Override
    public void run() {
        visitStart = System.currentTimeMillis();
        while (System.currentTimeMillis() < threadEndTime) {
            // Step 1: if in queue wait and get thirsty or hungry
            hungry = Math.random() < 0.01;
            thirsty = Math.random() < 0.02;
            updateHappiness(getHappinessDrop());
            if (!isInWaitingLine() && Math.random() < 0.7) {
                // if hungry -> go eat
                // if thirsty -> find drink
                Optional<WaitingLine<Visitor>> rollercoaster = themepark.chooseWaitingLine();
                if (rollercoaster.isEmpty()) {
                    updateHappiness(-5);
                } else {
                    rollercoaster.get().enterWaitingLine(this);
                    inWaitingLine = true;
                }
            }
            if (!isInWaitingLine() && thirsty && Math.random() < 0.5) {
                Optional<Shop> shop = themepark.getShop(ItemType.DRINKS);
                if (shop.isPresent() && shop.get().buy(this)) {
                    updateHappiness(10);
                    thirsty = false;
                } else {
                    updateHappiness(-5);
                }
            }
            if (!isInWaitingLine() && hungry && Math.random() < 0.5) {
                Optional<Shop> shop = themepark.getShop(ItemType.FOOD);
                if (shop.isPresent() && shop.get().buy(this)) {
                    updateHappiness(10);
                    hungry = false;
                } else {
                    updateHappiness(-5);
                }
            }
            if (!isInWaitingLine() && Math.random() < 0.1) {
                Optional<Shop> shop = themepark.getShop(ItemType.SOUVENIR);
                if (shop.isPresent() && shop.get().buy(this)) {
                    updateHappiness(5);
                }
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        visitEnd = System.currentTimeMillis();
    }

    private int getHappinessDrop() {
        int drop = -1;
        if (thirsty) {
            drop -= 1;
        }
        if (hungry) {
            drop -= 2;
        }
        return drop;
    }

    public void startVisit(Themepark themepark, long endTime) {
        this.themepark = themepark;
        try {
            themepark.payEntranceFee(this);
            threadEndTime = endTime;
            start();
        } catch (UnsufficientCashException e) {
            if (debug) {
                System.out.println(firstname + " cannot visit the park. Not enough cash.");
            }
        }
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "age=" + age +
                ", firstname='" + firstname + '\'' +
                ", cashAvailable=" + cashAvailable +
                ", hungery=" + hungry +
                ", thirty=" + thirsty +
                ", happinessLevel=" + happinessLevel +
                '}';
    }

    public void pay(double amount) {
        if (cashAvailable < amount) {
            throw new UnsufficientCashException();
        }
        cashAvailable -= amount;
        cashSpent += amount;
    }

    public boolean isInWaitingLine() {
        return inWaitingLine;
    }

    public void takeRide() {
        numberOfRides++;
        inWaitingLine = false;
        updateHappiness(10);
    }

    public int getNumberOfRides() {
        return numberOfRides;
    }

    public long getTimeInPark() {
        return visitEnd - visitStart;
    }
}
