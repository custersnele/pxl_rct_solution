package be.pxl.rct.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowShopTypesCommandTest {

    @Test
    public void returnsAllShopTypes() {
        ShowShopTypesCommand showShopTypesCommand = new ShowShopTypesCommand();
        List<String> result = showShopTypesCommand.execute();
        assertEquals("BALLOON_STALL [SOUVENIR]", result.get(0));
        assertEquals("SOUVENIR_SHOP [SOUVENIR]", result.get(1));
        assertEquals("BURGER_BAR [FOOD]", result.get(2));
        assertEquals("DRINKS_STALL [DRINKS]", result.get(3));
        assertEquals("ICECREAM_STALL [FOOD]", result.get(4));
        assertEquals("HOT_DOG_STALL [FOOD]", result.get(5));
        assertEquals("PIZZA_STALL [FOOD]", result.get(6));
    }
}
