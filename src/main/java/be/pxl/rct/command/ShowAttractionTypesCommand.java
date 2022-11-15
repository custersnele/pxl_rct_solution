package be.pxl.rct.command;

import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.AttractionType;
import be.pxl.rct.service.ThemeparkService;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class ShowAttractionTypesCommand implements Command<String> {

    private ThemeparkService themeparkService;
    private Scanner scanner;

    public ShowAttractionTypesCommand(ThemeparkService themeparkService, Scanner scanner) {
        this.themeparkService = themeparkService;
        this.scanner = scanner;
    }

    @Override
    public void execute(String filter) {
         themeparkService.getAttractionTypes().stream()
                 .filter(createPredicate(filter))
                 .map(displayAttractionType(filter))
                 .forEach(System.out::println);
    }

    private Function<AttractionType, String> displayAttractionType(String filter) {
            if (filter.contains("-all")) {
                return AttractionType::stringAllDetails;
            } else {
                return AttractionType::stringFewDetails;
            }
    }


    private Predicate<AttractionType> createPredicate(String filter) {
        String value = filter.substring(filter.indexOf(" ") + 1);
        if (filter.startsWith("-min-cost ")) {
            return a -> a.getCost() >= Integer.parseInt(value);
        } else if (filter.startsWith("-type ")) {
            return a -> a.getGenre().equals(RideGenre.valueOf(value));
        }
        return a -> true;
    }
}
