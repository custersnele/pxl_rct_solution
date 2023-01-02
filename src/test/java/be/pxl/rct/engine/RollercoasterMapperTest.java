package be.pxl.rct.engine;

import be.pxl.rct.attraction.Rating;
import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.attraction.Specification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RollercoasterMapperTest {

    @Test
    public void testMapLineToRollercoasterType() {
        RollercoasterType rollercoasterType = RollercoasterMapper.map("11;Merry-Go-Round;gentle ride;2;Low;1.2;Low;460;25;20");
        assertEquals(11, rollercoasterType.getId());
        assertEquals("Merry-Go-Round", rollercoasterType.getType());
        assertEquals(RideGenre.GENTLE_RIDE, rollercoasterType.getGenre());
        assertEquals(new Specification(2, Rating.LOW), rollercoasterType.getExcitement());
        assertEquals(new Specification(1.2, Rating.LOW), rollercoasterType.getNausea());
        assertEquals(460, rollercoasterType.getCost());
        assertEquals(25, rollercoasterType.getPassengers());
        assertEquals(20, rollercoasterType.getRunningTime());
    }
}
