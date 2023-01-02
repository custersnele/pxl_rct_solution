package be.pxl.rct.command;


import be.pxl.rct.themepark.Themepark;

public class ShowThemeparkDetails {

    public void execute(Themepark themepark) {
        themepark.printDetails();
    }
}
