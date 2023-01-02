package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowThemeparkDetailsTest {

    @Test
    public void throwsInvalidCommandExceptionWhenNoThemepark() {
        ShowThemeparkDetails showThemeparkDetails = new ShowThemeparkDetails();
        assertThrows(InvalidCommandException.class, () -> showThemeparkDetails.execute(null));
    }
}
