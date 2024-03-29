package be.pxl.rct.engine;

import be.pxl.rct.attraction.RideGenre;
import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.attraction.Rating;
import be.pxl.rct.attraction.Specification;

public class RollercoasterMapper {

    public static RollercoasterType map(String line) {
        String[] data = line.split(";");
        RollercoasterType attractionType = new RollercoasterType();
        attractionType.setId(Integer.parseInt(data[0]));
        attractionType.setType(data[1]);
        attractionType.setGenre(RideGenre.valueOf(data[2].replace(" ", "_").toUpperCase()));
        double excitement = Double.parseDouble(data[3]);
        String excitementRating = data[4].replace(" ", "_").toUpperCase();
        attractionType.setExcitement(new Specification(excitement, Rating.valueOf(excitementRating)));
        Rating nauseaRating = Rating.valueOf(data[6].toUpperCase());
        double nausea = Double.parseDouble(data[5]);
        attractionType.setNausea(new Specification(nausea, nauseaRating));
        attractionType.setCost(Double.parseDouble(data[7]));
        attractionType.setPassengers(Integer.parseInt(data[8]));
        attractionType.setRunningTime(Integer.parseInt(data[9]));
        return attractionType;
    }
}
