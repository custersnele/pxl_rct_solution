package be.pxl.rct.themepark;

import be.pxl.rct.shop.ShopType;
import be.pxl.rct.visitor.Visitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ThemeparkCloseTest {

    @Test
    public void closeThemepark() {
        Themepark themepark = new Themepark("test park", 1000);
        themepark.addShop("shop1", ShopType.ICECREAM_STALL);
        themepark.addShop("shop2", ShopType.SOUVENIR_SHOP);
        assertEquals(400, themepark.getCash());
        Visitor visitor = new Visitor("v1", 1000);
        for (int i = 0; i < 5; i++) {
            themepark.getShops().get(0).buy(visitor);
            themepark.getShops().get(1).buy(visitor);
        }
        themepark.close();
        assertEquals(410, themepark.getCash());
        assertFalse(themepark.isOpen());
    }
}
