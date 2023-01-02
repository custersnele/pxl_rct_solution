package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.shop.Shop;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddShopCommandTest {

    @Test
    public void cashIsReducedWithPriceOfShop() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddShopCommand addShopCommand = new AddShopCommand();
        addShopCommand.execute(themepark, "PIZZA_STALL Happy pizza's");
        assertEquals(themepark.getCash(), 2500 - ShopType.PIZZA_STALL.getCost());
    }

    @Test
    public void newShopIsAddedToTheThemepark() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddShopCommand addShopCommand = new AddShopCommand();
        addShopCommand.execute(themepark, "PIZZA_STALL Happy pizza's");
        assertEquals(1, themepark.getShops().size());
        Shop newShop = themepark.getShops().get(0);
        assertEquals(ShopType.PIZZA_STALL, newShop.getShopType());
        assertEquals("Happy pizza's", newShop.getName());
    }

    @Test
    public void anExceptionIsThrownWhenNoShopTypeIsGiven() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddShopCommand addShopCommand = new AddShopCommand();
        assertThrows(InvalidCommandException.class, () -> addShopCommand.execute(themepark, "Happy pizza's"));
    }


    @Test
    public void anExceptionIsThrownWhenAnInvalidShopTypeIsGiven() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddShopCommand addShopCommand = new AddShopCommand();
        assertThrows(InvalidCommandException.class, () -> addShopCommand.execute(themepark, "PIZZA_BAR Happy pizza's"));
    }

    @Test
    public void anExceptionIsThrownWhenNoNameIsGiven() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddShopCommand addShopCommand = new AddShopCommand();
        assertThrows(InvalidCommandException.class, () -> addShopCommand.execute(themepark, "PIZZA_STALL  "));
    }
}
