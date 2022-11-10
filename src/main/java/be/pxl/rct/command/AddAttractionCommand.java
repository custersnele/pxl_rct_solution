package be.pxl.rct.command;

import be.pxl.rct.attraction.AttractionType;
import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.service.ThemeparkService;

import java.util.Scanner;

public class AddAttractionCommand implements Command<String> {

    private ThemeparkService themeparkService;
    private Scanner scanner;

    public AddAttractionCommand(ThemeparkService themeparkService, Scanner scanner) {
        this.themeparkService = themeparkService;
        this.scanner = scanner;
    }

    @Override
    public void execute(String details) {
        // add-attraction type name
        String[] data = details.split(" ");
        try {
            int attractionTypeId = Integer.parseInt(data[1]);
            String attractionName = details.substring(details.replace("add-attraction ", "").indexOf(" ") + 1).trim();
            if (attractionName.isEmpty()) {
                throw new InvalidCommandException("New attraction should have a name.");
            }
            AttractionType attractionType = themeparkService.getAttractionTypes().stream()
                    .filter(a -> a.getId() == attractionTypeId)
                    .findAny()
                    .orElseThrow(() -> new InvalidCommandException("No attraction type with id [" + attractionTypeId + "]"));
            // TODO entree fee of attraction
            themeparkService.addAttraction(attractionType, attractionName, 5);
        } catch (NumberFormatException e) {
            System.out.println("Give the id of the type of attraction.");
        }
    }
}
