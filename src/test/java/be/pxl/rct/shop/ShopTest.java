package be.pxl.rct.shop;

import be.pxl.rct.visitor.Visitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    @Test
    public void sellsItemTypeTest() {
        Shop shop = new Shop("test shop", ShopType.ICECREAM_STALL);
        assertTrue(shop.sells(ItemType.FOOD));
        assertFalse(shop.sells(ItemType.SOUVENIR));
        assertFalse(shop.sells(ItemType.DRINKS));
    }

    @Test
    public void openShopAndBuyItemToVisitors() {
        Shop shop = new Shop("test shop", ShopType.ICECREAM_STALL);
        shop.open();
        Visitor visitor = new Visitor("v1", 500);
        shop.buy(visitor);
        shop.buy(visitor);
        assertEquals(2, shop.getItemsSold());
        assertEquals(2 * ShopType.ICECREAM_STALL.getProfitPerItem(), shop.getProfit());
        double paidByVisitor = ShopType.ICECREAM_STALL.getPricePerItem() * 2;
        assertEquals(paidByVisitor, visitor.getCashSpent());
        assertEquals(500 - paidByVisitor, visitor.getCashAvailable(), 0.01);
    }

    @Test
    public void returnsFalseIfVisitorHasUnsufficientCash() {
        Shop shop = new Shop("test shop", ShopType.ICECREAM_STALL);
        shop.open();
        Visitor visitor = new Visitor("v1", 0.5);
        assertFalse(shop.buy(visitor));
        assertEquals(0, shop.getItemsSold());
    }

    @Test
    public void getDayDetails() {
        Shop shop = new Shop("test shop", ShopType.ICECREAM_STALL);
        shop.open();
        Visitor visitor = new Visitor("v1", 500);
        shop.buy(visitor);
        shop.buy(visitor);
        assertEquals("test shop [ICECREAM_STALL] Items sold:2 Profit: 1.0", shop.getDayDetails());
    }
}
