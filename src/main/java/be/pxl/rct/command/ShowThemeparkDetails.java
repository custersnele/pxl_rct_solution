package be.pxl.rct.command;


import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;

public class ShowThemeparkDetails {

    public void execute(Themepark themepark) {
        if (themepark == null) {
            throw new InvalidCommandException("No themepark loaded or created.");
        }
        themepark.printDetails();
    }
}
