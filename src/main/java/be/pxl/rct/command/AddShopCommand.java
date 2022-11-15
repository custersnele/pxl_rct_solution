package be.pxl.rct.command;

import be.pxl.rct.attraction.AttractionType;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.service.ThemeparkService;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;

import java.util.Scanner;

public class AddShopCommand implements Command<String> {

    private Themepark themepark;
    private ThemeparkService themeparkService;
    private Scanner scanner;

    public AddShopCommand(ThemeparkService themeparkService, Scanner scanner) {
        this.themeparkService = themeparkService;
        this.scanner = scanner;
    }

    @Override
    public void execute(String details) {
        // add-attraction type name
        String[] data = details.split(" ");
        try {
            ShopType shopType = ShopType.valueOf(data[1]);
            String shopName = details.substring(details.replace("add-shop ", "").indexOf(" ") + 1).trim();
            if (shopName.isEmpty()) {
                throw new InvalidCommandException("New shop should have a name.");
            }
            themeparkService.addShop(shopType, shopName);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Give the id of the type of attraction.");
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Invalid shop type [" + data[1]);
        }
    }
}
