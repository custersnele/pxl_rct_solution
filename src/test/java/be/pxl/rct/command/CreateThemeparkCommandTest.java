package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateThemeparkCommandTest {

    @Test
    public void createANewThemeparkWithInitialCashAndGivenName() {
        CreateThemeparkCommand createThemeparkCommand = new CreateThemeparkCommand(5000);
        Themepark testPark = createThemeparkCommand.execute(" test park  ");
        assertEquals("test park", testPark.getName());
        assertEquals(5000, testPark.getCash());
        assertEquals(0, testPark.getRollerCoasters().size());
        assertEquals(0, testPark.getShops().size());
    }

    @Test
    public void anExceptionIsThrownWhenNoNameIsGiven() {
        CreateThemeparkCommand createThemeparkCommand = new CreateThemeparkCommand(5000);
        assertThrows(InvalidCommandException.class, () -> createThemeparkCommand.execute(" "));
    }
}
