package be.pxl.rct.command;

import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAttractionCommandTest {

    private List<RollercoasterType> rollercoasterTypes = new ArrayList<>();

    @BeforeEach
    public void init() {
       rollercoasterTypes.add(new RollercoasterType(1, RideGenre.GENTLE_RIDE, 1500));
       rollercoasterTypes.add(new RollercoasterType(2, RideGenre.TRANSPORT, 3000));
    }

    @Test
    public void cashIsReducedWithPriceOfRollercoaster() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddAttractionCommand addAttractionCommand = new AddAttractionCommand(rollercoasterTypes);
        addAttractionCommand.execute(themepark, "1 first attraction");
        assertEquals(themepark.getCash(), 1000);
    }

    @Test
    public void newRollercoasterIsAddedToTheThemepark() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddAttractionCommand addAttractionCommand = new AddAttractionCommand(rollercoasterTypes);
        addAttractionCommand.execute(themepark, "1 first attraction");
        assertEquals(1, themepark.getRollerCoasters().size());
        RollerCoaster newRollercoaster = themepark.getRollerCoasters().get(0);
        assertEquals(1, newRollercoaster.getAttractionType().getId());
        assertEquals(RideGenre.GENTLE_RIDE, newRollercoaster.getAttractionType().getGenre());
        assertEquals("first attraction", newRollercoaster.getName());
    }

    @Test
    public void anExceptionIsThrownWhenNoIdIsGiven() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddAttractionCommand addAttractionCommand = new AddAttractionCommand(rollercoasterTypes);
        assertThrows(InvalidCommandException.class, () -> addAttractionCommand.execute(themepark, "first attraction"));
    }


    @Test
    public void anExceptionIsThrownWhenAnInvalidIdIsGiven() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddAttractionCommand addAttractionCommand = new AddAttractionCommand(rollercoasterTypes);
        assertThrows(InvalidCommandException.class, () -> addAttractionCommand.execute(themepark, "8 first attraction"));
    }

    @Test
    public void anExceptionIsThrownWhenNoNameIsGiven() {
        Themepark themepark = new Themepark("testpark", 2500);
        AddAttractionCommand addAttractionCommand = new AddAttractionCommand(rollercoasterTypes);
        assertThrows(InvalidCommandException.class, () -> addAttractionCommand.execute(themepark, "1  "));
    }
}
