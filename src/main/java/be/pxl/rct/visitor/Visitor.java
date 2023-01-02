package be.pxl.rct.visitor;

import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.attraction.WaitingLine;
import be.pxl.rct.exception.NoCashException;
import be.pxl.rct.shop.ItemType;
import be.pxl.rct.shop.Shop;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;

import java.util.Optional;
import java.util.Random;

public class Visitor extends Thread {
    private static final Random RANDOM = new Random();
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

    private Themepark themepark;

    public Visitor() {
    }

    public Visitor(String firstname, double cashAvailable) {
        this.firstname = firstname;
        this.cashAvailable = cashAvailable;
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
            happinessLevel -= getHappinessDrop();
            if (!isInWaitingLine() && Math.random() < 0.7) {
                // if hungry -> go eat
                // if thirsty -> find drink
                Optional<WaitingLine<Visitor>> rollercoaster = themepark.chooseWaitingLine();
                if (rollercoaster.isEmpty()) {
                    happinessLevel -= 5;
                } else {
                    rollercoaster.get().enterWaitingLine(this);
                    inWaitingLine = true;
                }
            }
            if (!isInWaitingLine() && thirsty && Math.random() < 0.5) {
                Optional<Shop> shop = themepark.getShop(ItemType.DRINKS);
                if (shop.isPresent() && shop.get().buy(this)) {
                    happinessLevel += 10;
                    thirsty = false;
                } else {
                    happinessLevel -= 5;
                }
            }
            if (!isInWaitingLine() && hungry && Math.random() < 0.5) {
                Optional<Shop> shop = themepark.getShop(ItemType.FOOD);
                if (shop.isPresent() && shop.get().buy(this)) {
                    happinessLevel += 10;
                    hungry = false;
                } else {
                    happinessLevel -= 5;
                }
            }
            if (!isInWaitingLine() && Math.random() < 0.1) {
                Optional<Shop> shop = themepark.getShop(ItemType.SOUVENIR);
                if (shop.isPresent() && shop.get().buy(this)) {
                    happinessLevel += 5;
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
        int drop = 1;
        if (thirsty) {
            drop++;
        }
        if (hungry) {
            drop += 2;
        }
        return drop;
    }

    public void startVisit(Themepark themepark, int timeInPark) {
        this.themepark = themepark;
        try {
            themepark.payEntranceFee(this);
            threadEndTime = System.currentTimeMillis() + timeInPark;
            start();
        } catch (NoCashException e) {
            // WHAT TO DO WITH THIS EXCEPTION
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
        // TODO test this method
        if (cashAvailable < amount) {
            // TODO update message
            throw new NoCashException("Unsuficient cash");
        }
        cashAvailable -= amount;
        cashSpent += amount;
    }

    public boolean isInWaitingLine() {
        return inWaitingLine;
    }

    public void takeRide(RollerCoaster rollerCoaster) {
        numberOfRides++;
        inWaitingLine = false;
        updateHappiness(10); // TODO how much increases hapinesslevel? depending on excitement
        // TODO person becomes sick and goes home
    }

    public int getNumberOfRides() {
        return numberOfRides;
    }
}
