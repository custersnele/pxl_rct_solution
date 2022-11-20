package be.pxl.rct.command;


import be.pxl.rct.themepark.Themepark;

import java.util.Scanner;

public class ShowThemeparkDetails implements Command<String> {
    private Themepark themepark;

    public ShowThemeparkDetails(Themepark themepark) {
        this.themepark = themepark;
    }
    @Override
    public void execute(String data) {
        themepark.printDetails();

    }
}
