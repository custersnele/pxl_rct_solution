package be.pxl.rct.command;

import be.pxl.rct.shop.ShopType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowShopTypesCommand {

    public List<String> execute() {
        List<String> result = new ArrayList<>();
        Arrays.stream(ShopType.values()).forEach(shopType -> result.add(shopType.ordinal() + " " + shopType.name() + "[" + shopType.getItemType()));
        return result;
    }
}
