package be.pxl.rct.attraction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RollercoasterTest {

    @Test
    public void toStringTest() {
        RollerCoaster rollerCoaster = new RollerCoaster("first rollercoaster", new RollercoasterType(5, "fast roller coaster", RideGenre.ROLLER_COASTER, 1500));
        assertEquals("first rollercoaster [fast roller coaster, ROLLER_COASTER]", rollerCoaster.toString());
    }
}
