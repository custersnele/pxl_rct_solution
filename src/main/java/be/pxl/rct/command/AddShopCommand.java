package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;

public class AddShopCommand {

    public void execute(Themepark themepark, String details) {
        String[] data = details.split(" ");
        try {
            ShopType shopType = ShopType.valueOf(data[0]);
            String shopName = details.substring(details.indexOf(" ") + 1).trim();
            if (shopName.isEmpty()) {
                throw new InvalidCommandException("New shop should have a name.");
            }
            themepark.addShop(shopName, shopType);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Invalid shop type [" + data[1]);
        }
    }
}
