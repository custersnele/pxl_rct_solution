package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetCommandTest {

    @Test
    public void setEntranceFeeWithValidValue() {
        Themepark themepark = new Themepark("test park", 5000);
        SetCommand setCommand = new SetCommand();
        setCommand.execute(themepark, "entrance-fee 35.5");
        assertEquals(35.5, themepark.getEntranceFee());
    }

    @Test
    public void setEntranceFeeWithInvalidValueThrowsAnException() {
        Themepark themepark = new Themepark("test park", 5000);
        SetCommand setCommand = new SetCommand();
        assertThrows(InvalidCommandException.class, () -> setCommand.execute(themepark, "entrance-fee abc"));
    }

    @Test
    public void setCommandWithInvalidPropertyThrowsAnException() {
        Themepark themepark = new Themepark("test park", 5000);
        SetCommand setCommand = new SetCommand();
        assertThrows(InvalidCommandException.class, () -> setCommand.execute(themepark, "something abc"));
    }
}
