package be.pxl.rct.attraction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RollercoasterTest {

    @Test
    public void toStringTest() {
        RollerCoaster rollerCoaster = new RollerCoaster("first rollercoaster", new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500));
        assertEquals("first rollercoaster [fast roller coaster, ROLLER_COASTER]", rollerCoaster.toString());
    }

    @Test
    public void compareToReturnsZeroIfEqual() {
        RollerCoaster rollerCoaster = new RollerCoaster("ABC", new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500));
        RollerCoaster other = new RollerCoaster("abc", new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500));
        assertEquals(0, rollerCoaster.compareTo(other));
    }

    @Test
    public void compareToReturnsNegativeIfAlphabeticallyBefore() {
        RollerCoaster rollerCoaster = new RollerCoaster("ABC", new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500));
        RollerCoaster other = new RollerCoaster("abX", new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500));
        assertTrue(rollerCoaster.compareTo(other) < 0);
        assertTrue(other.compareTo(rollerCoaster) > 0);
    }

    @Test
    public void getAllowed() {
        RollercoasterType type = new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500);
        type.setPassengers(15);
        type.setRunningTime(20);
        RollerCoaster rollerCoaster = new RollerCoaster("ABC", type);
        assertEquals(15, rollerCoaster.getAllowed());
        assertEquals(20, rollerCoaster.getTime());
    }

}
