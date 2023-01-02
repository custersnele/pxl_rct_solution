package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;

public class AddShopCommand {

    private final Themepark themepark;

    public AddShopCommand(Themepark themepark) {
        this.themepark = themepark;
    }

    public void execute(String details) {
        // add-attraction type name
        String[] data = details.split(" ");
        try {
            ShopType shopType = ShopType.valueOf(data[1]);
            String filterName = details.replace("add-shop ", "");
            String shopName = filterName.substring(filterName.indexOf(" ") + 1).trim();
            if (shopName.isEmpty()) {
                throw new InvalidCommandException("New shop should have a name.");
            }
            themepark.addShop(shopName, shopType);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Give the id of the type of attraction.");
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Invalid shop type [" + data[1]);
        }
    }
}
