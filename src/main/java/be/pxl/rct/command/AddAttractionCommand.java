package be.pxl.rct.command;

import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;

import java.util.List;

public class AddAttractionCommand {

    private List<RollercoasterType> attractionTypes;

    public AddAttractionCommand(List<RollercoasterType> attractionTypes) {
        this.attractionTypes = attractionTypes;
    }
    public void execute(Themepark themepark, String details) {
        String[] data = details.split(" ");
        try {
            int attractionTypeId = Integer.parseInt(data[0]);
            String attractionName = details.substring(details.indexOf(" ") + 1).trim();
            if (attractionName.isEmpty()) {
                throw new InvalidCommandException("New attraction should have a name.");
            }
            RollercoasterType attractionType = attractionTypes.stream()
                    .filter(a -> a.getId() == attractionTypeId)
                    .findAny()
                    .orElseThrow(() -> new InvalidCommandException("No attraction type with id [" + attractionTypeId + "]"));
            themepark.addAttraction(attractionName, attractionType);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Give the id of the type of attraction.");
        }
    }
}
