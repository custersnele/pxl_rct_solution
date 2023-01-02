package be.pxl.rct.themepark;

import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.attraction.WaitingLine;
import be.pxl.rct.exception.UnsufficientCashException;
import be.pxl.rct.shop.ItemType;
import be.pxl.rct.shop.Shop;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.visitor.Visitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Themepark implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private double cash;

    private double entranceFee;
    private int daysOpen;
    private transient boolean open;
    private List<RollerCoaster> rollerCoasters = new ArrayList<>();
    private List<Shop> shops = new ArrayList<>();
    private transient List<WaitingLine<Visitor>> waitingLines;

    public Themepark(String name, double cash) {
        this.name = name;
        this.cash = cash;
    }

    public double getEntranceFee() {
        return entranceFee;
    }

    public double getCash() {
        return cash;
    }

    public void pay(double amount) {
        cash -= amount;
    }

    public String getName() {
        return name;
    }


    public void addAttraction(String name, RollercoasterType attractionType) {
        if (attractionType.getCost() > getCash()) {
            throw new UnsufficientCashException();
        }
        pay(attractionType.getCost());
        rollerCoasters.add(new RollerCoaster(name, attractionType));
    }

    public void addShop(String name, ShopType shopType) {
        if (shopType.getCost() > getCash()) {
            throw new UnsufficientCashException();
        }
        pay(shopType.getCost());
        shops.add(new Shop(name, shopType));
    }

    public void printDetails() {
        System.out.println("Name: " + name);
        System.out.println("Cash: " + cash);
        System.out.println("Days open: " + daysOpen);
        System.out.println("Number of rollercoasters: " + rollerCoasters.size());
        rollerCoasters.stream().sorted().forEach(RollerCoaster::showDetails);
        System.out.println("Number of shops: " + shops.size());
        shops.stream().sorted().forEach(Shop::showDetails);
    }

    public Optional<WaitingLine<Visitor>> chooseWaitingLine() {
        return waitingLines.stream().min((r1, r2) -> Integer.compare(r1.getWaitingLineSize(), r2.getWaitingLineSize()));
    }

    public List<RollerCoaster> getRollerCoasters() {
        return rollerCoasters;
    }

    public void setEntranceFee(double entranceFee) {
        this.entranceFee = entranceFee;
    }

    public void open(long closingTime) {
        daysOpen++;
        open = true;
        waitingLines = new ArrayList<>();
        for (RollerCoaster rollerCoaster : rollerCoasters) {
            WaitingLine<Visitor> visitorWaitingLine = new WaitingLine<>(rollerCoaster);
            waitingLines.add(visitorWaitingLine);
            visitorWaitingLine.setClosingTime(closingTime);
            visitorWaitingLine.start();
        }
    }

    public void close() {
        open = false;
        // take money from shops
        getShops().forEach(s -> addCash(s.getProfit()));
    }

    public boolean isOpen() {
        return open;
    }

    public void addCash(double amount) {
        cash += amount;
    }

    public int getDaysOpen() {
        return daysOpen;
    }

    public void payEntranceFee(Visitor visitor) {
        visitor.pay(entranceFee);
        addCash(entranceFee);
    }

    public Optional<Shop> getShop(ItemType itemType) {
        return shops.stream().filter(s -> s.sells(itemType)).findAny();
    }

    public List<Shop> getShops() {
        return shops;
    }


}
