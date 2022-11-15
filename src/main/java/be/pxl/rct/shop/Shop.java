package be.pxl.rct.shop;

import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;

public class Shop {
    private String name;
    private ShopType shopType;
    private Themepark themepark;

    public Shop(String name, ShopType shopType, Themepark themepark) {
        this.name = name;
        this.shopType = shopType;
        this.themepark = themepark;
    }

    public void buy(Visitor vistor) {
        vistor.pay(shopType.getPricePerItem());
        themepark.addCash(shopType.getProfitPerItem());
    }

    public void showDetails() {
        System.out.println("*** " + name + " [" + shopType + "]");
    }
}
