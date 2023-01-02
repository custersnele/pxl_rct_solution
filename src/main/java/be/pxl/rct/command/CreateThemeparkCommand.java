package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;

public class CreateThemeparkCommand {

    private final double initialCash;

    public CreateThemeparkCommand(double initialCash) {
        this.initialCash = initialCash;
    }

    public Themepark execute(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidCommandException("The new themepark should have a name.");
        }
        return new Themepark(name.trim(), initialCash);

    }
}
