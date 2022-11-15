package be.pxl.rct.visitor;

import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.exception.NoCashException;
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
    private boolean thirty;
    private boolean inWaitingLine;
    private int happinessLevel;

    private long threadEndTime;

    private Themepark themepark;

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

    public void setCashSpent(double cashSpent) {
        this.cashSpent = cashSpent;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    public boolean isThirty() {
        return thirty;
    }

    public void setThirty(boolean thirty) {
        this.thirty = thirty;
    }

    public int getHappinessLevel() {
        return happinessLevel;
    }

    public void setHappinessLevel(int happinessLevel) {
        this.happinessLevel = happinessLevel;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < threadEndTime) {
            // Step 1: if in queue wait and get thirsty or hungry
            happinessLevel -= 1;
            if (!isInWaitingLine() && Math.random() < 0.7) {
                // if hungry -> go eat
                // if thirsty -> find drink
                Optional<RollerCoaster> rollercoaster = themepark.findRollercoaster(this);
                if (rollercoaster.isEmpty()) {
                    happinessLevel -= 5;
                } else {
                    rollercoaster.get().enterWaitingLine(this);
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        leavePark();
    }

    private void leavePark() {
        themepark.removeVisitor(this);
    }

    public void startVisit(Themepark themepark, int timeInPark) {
        this.themepark = themepark;
        threadEndTime = System.currentTimeMillis() + timeInPark;
        start();
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "age=" + age +
                ", firstname='" + firstname + '\'' +
                ", cashAvailable=" + cashAvailable +
                ", hungery=" + hungry +
                ", thirty=" + thirty +
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

    public void setInWaitingLine(boolean inWaitingLine) {
        this.inWaitingLine = inWaitingLine;
    }

    public boolean isInWaitingLine() {
        return inWaitingLine;
    }

    public void takeRide(RollerCoaster rollerCoaster) {
        inWaitingLine = false;
        happinessLevel += 10; // TODO how much increases hapinesslevel? depending on excitement
        // TODO person becomes sick and goes home
    }
}
