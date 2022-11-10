package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;

public class LoadThemeparkCommand implements Command<String> {

    private Themepark themepark;
    private double initialCash;

    public LoadThemeparkCommand(double initialCash) {
        this.initialCash = initialCash;
    }

    @Override
    public void execute(String name) {
        try {
            // TODO initial cash
            themepark = new Themepark(name, initialCash);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("load failed. " + e.getMessage(), e);
        }
    }

    public Themepark getThemepark() {
        return themepark;
    }
}
