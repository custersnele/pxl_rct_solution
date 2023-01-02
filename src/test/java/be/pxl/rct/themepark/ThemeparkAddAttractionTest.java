package be.pxl.rct.themepark;

import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.exception.UnsufficientCashException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThemeparkAddAttractionTest {

    @Test
    public void aRollercoasterIsAddedToTheThemepark() {
        Themepark themepark = new Themepark("test park", 5000);
        themepark.addAttraction("first attraction", new RollercoasterType(5, RideGenre.GENTLE_RIDE, 1000));
        assertEquals(1, themepark.getRollerCoasters().size());
        RollerCoaster newRollercoaster = themepark.getRollerCoasters().get(0);
        assertEquals(5, newRollercoaster.getAttractionType().getId());
        assertEquals(RideGenre.GENTLE_RIDE, newRollercoaster.getAttractionType().getGenre());
        assertEquals("first attraction", newRollercoaster.getName());
    }

    @Test
    public void thePriceOfTheRollercoasterIsPaid() {
        Themepark themepark = new Themepark("test park", 5000);
        themepark.addAttraction("first attraction", new RollercoasterType(5, RideGenre.GENTLE_RIDE, 1000));
        assertEquals(4000, themepark.getCash());
    }

    @Test
    public void anExceptionIsThrownWhenTheRollercoasterCannotBePaid() {
        Themepark themepark = new Themepark("test park", 1000);
        assertThrows(UnsufficientCashException.class, () -> themepark.addAttraction("first attraction", new RollercoasterType(5, RideGenre.GENTLE_RIDE, 1500)));
    }
}
