package be.pxl.rct.command;

import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.exception.InvalidCommandException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ShowRollercoasterTypesCommand {

    private List<RollercoasterType> attractionTypes;

    public ShowRollercoasterTypesCommand(List<RollercoasterType> attractionTypes) {
        this.attractionTypes = attractionTypes;
    }

    public List<String> execute(String filter) {
        Map<String, String> values = createHashMap(filter);
        return attractionTypes.stream()
                .filter(createPredicate(values))
                .sorted(createComparator(values))
                .map(displayAttractionType(filter))
                .toList();
    }

    private Function<RollercoasterType, String> displayAttractionType(String filter) {
        if (filter.contains("-all")) {
            return RollercoasterType::getAllDetails;
        } else {
            return RollercoasterType::getDetails;
        }
    }

    private Comparator<RollercoasterType> createComparator(Map<String, String> filter) {
        if (filter.containsKey("-sorted")) {
            String sort = filter.get("-sorted");
            switch (sort) {
                case "name":
                    return (t1, t2) -> t1.getType().compareTo(t2.getType());
                case "id":
                    return (t1, t2) -> Integer.compare(t1.getId(), t2.getId());
                default:
                    throw new InvalidCommandException("Invalid sorting...");
            }
        } else {
            return (t1, t2) -> Integer.compare(t1.getId(), t2.getId());
        }
    }


    private Predicate<RollercoasterType> createPredicate(Map<String, String> values) {
        try {
            Predicate<RollercoasterType> filter = a -> true;
            if (values.containsKey("-min-cost")) {
                double value = Double.parseDouble(values.get("-min-cost"));
                filter = filter.and(a -> a.getCost() >= value);
            }
            if (values.containsKey("-max-cost")) {
                double value = Double.parseDouble(values.get("-max-cost"));
                filter = filter.and(a -> a.getCost() <= value);
            }
            if (values.containsKey("-ride")) {
                RideGenre genre = RideGenre.valueOf(values.get("-ride"));
                filter = filter.and(a -> a.getGenre().equals(genre));
            }
            return filter;
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Filter not valid.");
        }
    }

    private Map<String, String> createHashMap(String filter) {
        Map<String, String> map = new HashMap<>();
        if (filter.isBlank()) {
            return map;
        }
        String[] parts = filter.split(" ");
        int i = 0;
        while (i < parts.length) {
            if (parts[i].trim().equals("-all")) {
                map.put(parts[i].trim(), "");
                i++;
            } else {
                map.put(parts[i].trim(), parts[i + 1].trim());
                i += 2;
            }
        }
        return map;
    }
}
