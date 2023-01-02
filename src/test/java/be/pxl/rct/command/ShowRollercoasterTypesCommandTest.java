package be.pxl.rct.command;

import be.pxl.rct.attraction.Rating;
import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.attraction.Specification;
import be.pxl.rct.exception.InvalidCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowRollercoasterTypesCommandTest {

    private List<RollercoasterType> rollercoasterTypes;

    @BeforeEach
    public void init() {
        rollercoasterTypes = new ArrayList<>();
        RollercoasterType abc = new RollercoasterType(1, "abc", RideGenre.ROLLER_COASTER, 520);
        abc.setNausea(new Specification(3.5, Rating.LOW));
        abc.setExcitement(new Specification(6.5, Rating.VERY_HIGH));
        rollercoasterTypes.add(abc);
        rollercoasterTypes.add(new RollercoasterType(3, "xyz", RideGenre.GENTLE_RIDE, 850));
        rollercoasterTypes.add(new RollercoasterType(2, "uvw", RideGenre.TRANSPORT, 1500));
        rollercoasterTypes.add(new RollercoasterType(4, "acd", RideGenre.ROLLER_COASTER, 1000));
    }

    @Test
    public void defaultSortedById() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        List<String> result = showRollercoasterTypesCommand.execute("");
        assertEquals("[1] abc ROLLER_COASTER 520.0", result.get(0));
        assertEquals("[2] uvw TRANSPORT 1500.0", result.get(1));
        assertEquals("[3] xyz GENTLE_RIDE 850.0", result.get(2));
        assertEquals("[4] acd ROLLER_COASTER 1000.0", result.get(3));
    }

    @Test
    public void resultCanBeSortedById() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        List<String> result = showRollercoasterTypesCommand.execute("-sorted id");
        assertEquals("[1] abc ROLLER_COASTER 520.0", result.get(0));
        assertEquals("[2] uvw TRANSPORT 1500.0", result.get(1));
        assertEquals("[3] xyz GENTLE_RIDE 850.0", result.get(2));
        assertEquals("[4] acd ROLLER_COASTER 1000.0", result.get(3));
    }

    @Test
    public void resultCanBeSortedByName() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        List<String> result = showRollercoasterTypesCommand.execute("-all -sorted name");
        assertEquals("[1] abc ROLLER_COASTER E: VERY_HIGH(6.5) N: LOW(3.5) 520.0", result.get(0));
        assertEquals("[4] acd ROLLER_COASTER E: null N: null 1000.0", result.get(1));
        assertEquals("[2] uvw TRANSPORT E: null N: null 1500.0", result.get(2));
        assertEquals("[3] xyz GENTLE_RIDE E: null N: null 850.0", result.get(3));

    }


    @Test
    public void canBeFilteredByMinAndMaxCost() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        List<String> result = showRollercoasterTypesCommand.execute("-min-cost 800 -max-cost 1000");
        assertEquals("[3] xyz GENTLE_RIDE 850.0", result.get(0));
        assertEquals("[4] acd ROLLER_COASTER 1000.0", result.get(1));
    }

    @Test
    public void canBeFilteredByRide() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        List<String> result = showRollercoasterTypesCommand.execute("-ride TRANSPORT");
        assertEquals("[2] uvw TRANSPORT 1500.0", result.get(0));
    }

    @Test
    public void anExceptionIsThrownWhenInvalidSortValue() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        assertThrows(InvalidCommandException.class, () -> showRollercoasterTypesCommand.execute("-all -sorted something"));
    }

    @Test
    public void anExceptionIsThrownWhenInvalidValue() {
        ShowRollercoasterTypesCommand showRollercoasterTypesCommand = new ShowRollercoasterTypesCommand(rollercoasterTypes);
        assertThrows(InvalidCommandException.class, () -> showRollercoasterTypesCommand.execute("-min-cost abc -max-cost 1000"));

    }
}
