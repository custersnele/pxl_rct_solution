package be.pxl.rct.themepark;

import be.pxl.rct.exception.UnsufficientCashException;
import be.pxl.rct.shop.Shop;
import be.pxl.rct.shop.ShopType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThemeparkAddShopTest {

    @Test
    public void aShopIsAddedToTheThemepark() {
        Themepark themepark = new Themepark("test park", 5000);
        themepark.addShop("first shop", ShopType.ICECREAM_STALL);
        assertEquals(1, themepark.getShops().size());
        Shop newShop = themepark.getShops().get(0);
        assertEquals(ShopType.ICECREAM_STALL, newShop.getShopType());
        assertEquals("first shop", newShop.getName());
    }

    @Test
    public void thePriceOfTheShopIsPaid() {
        Themepark themepark = new Themepark("test park", 5000);
        themepark.addShop("first shop", ShopType.ICECREAM_STALL);
        assertEquals(4750, themepark.getCash());
    }

    @Test
    public void anExceptionIsThrownWhenTheShopCannotBePaid() {
        Themepark themepark = new Themepark("test park", 100);
        assertThrows(UnsufficientCashException.class, () -> themepark.addShop("first shop", ShopType.ICECREAM_STALL));
    }
}
