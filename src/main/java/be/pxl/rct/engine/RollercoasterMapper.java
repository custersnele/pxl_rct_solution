package be.pxl.rct.engine;

import be.pxl.rct.attraction.AttractionGenre;
import be.pxl.rct.attraction.AttractionType;
import be.pxl.rct.attraction.Rating;
import be.pxl.rct.attraction.Specification;

public class RollercoasterMapper {

    public static AttractionType map(String line) {
        String[] data = line.split(";");
        AttractionType attractionType = new AttractionType();
        attractionType.setId(Integer.parseInt(data[0]));
        attractionType.setType(data[1]);
        attractionType.setGenre(AttractionGenre.valueOf(data[2].replace(" ", "_").toUpperCase()));
        double excitement = Double.parseDouble(data[3]);
        String excitementRating = data[4].replace(" ", "_").toUpperCase();
        attractionType.setExcitement(new Specification(excitement, Rating.valueOf(excitementRating)));
        Rating nauseaRating = Rating.valueOf(data[6].toUpperCase());
        double nausea = Double.parseDouble(data[5]);
        attractionType.setNausea(new Specification(nausea, nauseaRating));

        attractionType.setCost(Double.parseDouble(data[7]));
        attractionType.setPassengers(Integer.parseInt(data[8]));
        // TODO update running time
        attractionType.setRunningTime(25);
        return attractionType;
    }
}
