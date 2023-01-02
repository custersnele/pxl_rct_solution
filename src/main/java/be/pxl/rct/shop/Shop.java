package be.pxl.rct.shop;

import be.pxl.rct.exception.NoCashException;
import be.pxl.rct.visitor.Visitor;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Shop implements Serializable, Comparable<Shop> {
    private static final long serialVersionUUID = 1L;
    private final String name;
    private final ShopType shopType;
    private AtomicInteger itemsSold = new AtomicInteger();

    public Shop(String name, ShopType shopType) {
        this.name = name;
        this.shopType = shopType;
    }

    public ShopType getShopType() {
        return shopType;
    }

    public String getName() {
        return name;
    }

    public boolean sells(ItemType itemType) {
        return shopType.hasItemType(itemType);
    }

    public boolean buy(Visitor vistor) {
        try {
            vistor.pay(shopType.getPricePerItem());
            itemsSold.incrementAndGet();
            return true;
        } catch (NoCashException e) {
            return false;
        }
    }

    public void open() {
        itemsSold = new AtomicInteger();
    }

    public double getProfit() {
        return getItemsSold() * shopType.getProfitPerItem();
    }

    public void showDetails() {
        System.out.println("*** " + toString());
    }

    public int getItemsSold() {
        return itemsSold.get();
    }

    public String getDayDetails() {
        return name + " [" + shopType + "] Items sold:" + getItemsSold() + " Profit: " + getProfit();
    }

    @Override
    public String toString() {
        return name + " [" + shopType + "]";
    }

    @Override
    public int compareTo(Shop shop) {
        return String.CASE_INSENSITIVE_ORDER.compare(name, shop.name);
    }
}
