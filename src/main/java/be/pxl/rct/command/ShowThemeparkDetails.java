package be.pxl.rct.command;

import be.pxl.rct.service.ThemeparkService;

import java.util.Scanner;

public class ShowThemeparkDetails implements Command<String> {
    private ThemeparkService themeparkService;
    private Scanner scanner;

    public ShowThemeparkDetails(ThemeparkService themeparkService) {
        this.themeparkService = themeparkService;
    }
    @Override
    public void execute(String data) {
        themeparkService.getThemepark().showVisitorDetails();

    }
}
