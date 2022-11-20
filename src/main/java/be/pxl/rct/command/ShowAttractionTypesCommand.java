package be.pxl.rct.command;

import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.attraction.RideGenre;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ShowAttractionTypesCommand implements Command<String> {

    private List<RollercoasterType> attractionTypes;

    public ShowAttractionTypesCommand(List<RollercoasterType> attractionTypes) {
        this.attractionTypes = attractionTypes;
    }

    @Override
    public void execute(String filter) {
         attractionTypes.stream()
                 .filter(createPredicate(filter))
                 .map(displayAttractionType(filter))
                 .forEach(System.out::println);
    }

    private Function<RollercoasterType, String> displayAttractionType(String filter) {
            if (filter.contains("-all")) {
                return RollercoasterType::getAllDetails;
            } else {
                return RollercoasterType::getDetails;
            }
    }


    private Predicate<RollercoasterType> createPredicate(String filter) {
        String value = filter.substring(filter.indexOf(" ") + 1);
        if (filter.startsWith("-min-cost ")) {
            return a -> a.getCost() >= Integer.parseInt(value);
        } else if (filter.startsWith("-type ")) {
            return a -> a.getGenre().equals(RideGenre.valueOf(value));
        }
        return a -> true;
    }
}
