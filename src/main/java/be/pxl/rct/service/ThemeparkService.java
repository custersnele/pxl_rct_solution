package be.pxl.rct.service;

import be.pxl.rct.attraction.AttractionType;
import be.pxl.rct.attraction.RollerCoaster;
import be.pxl.rct.engine.RollercoasterMapper;
import be.pxl.rct.exception.UnsufficientCashException;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ThemeparkService {

    private Themepark themepark;
    private List<AttractionType> attractionTypes = new ArrayList<>();

    public void init(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // is
            while ((line = reader.readLine()) != null) {
                AttractionType attractionType = RollercoasterMapper.map(line);
                attractionTypes.add(attractionType);
            }
        } catch (IOException e) {
            System.out.println("Error initializing the game!");
        }
    }

    public List<AttractionType> getAttractionTypes() {
        return attractionTypes;
    }

    public void setThemepark(Themepark themepark) {
        this.themepark = themepark;
    }


    public Themepark getThemepark() {
        return themepark;
    }

    public void addAttraction(AttractionType attractionType, String name, double entree) {
        themepark.addAttraction(name, attractionType, entree);
    }

    public void addShop(ShopType shopType, String shopName) {
        themepark.addShop(shopName, shopType);
    }
}
