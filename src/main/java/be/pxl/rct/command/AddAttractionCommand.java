package be.pxl.rct.command;

import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;

import java.util.List;

public class AddAttractionCommand implements Command<String> {

    private Themepark themepark;
    private List<RollercoasterType> attractionTypes;

    public AddAttractionCommand(Themepark themepark, List<RollercoasterType> attractionTypes) {
        this.themepark = themepark;
        this.attractionTypes = attractionTypes;
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
            RollercoasterType attractionType = attractionTypes.stream()
                    .filter(a -> a.getId() == attractionTypeId)
                    .findAny()
                    .orElseThrow(() -> new InvalidCommandException("No attraction type with id [" + attractionTypeId + "]"));
            // TODO entree fee of attraction
            themepark.addAttraction(attractionName, attractionType);
        } catch (NumberFormatException e) {
            System.out.println("Give the id of the type of attraction.");
        }
    }
}
