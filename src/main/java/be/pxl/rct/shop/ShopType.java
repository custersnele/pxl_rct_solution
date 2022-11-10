package be.pxl.rct.shop;

public enum ShopType {

    BALLOON_STALL(250, 1.0, 0.7),
    BURGER_BAR(300, 1.5, 1),
    // Cash Machine;
    DRINKS_STALL(225, 1.20, 0.9),
    // First Aid Room;
    ICECREAM_STALL(250, 0.9, 0.5),
    HOT_DOG_STALL(290, 1.0, 0.5),
    //Information Kiosk;
    PIZZA_STALL(275, 1.6, 1);

    private double cost;
    private double price_per_item;
    private double profit_per_item;

    ShopType(double cost, double price_per_item, double profit_per_item) {
        this.cost = cost;
        this.price_per_item = price_per_item;
        this.profit_per_item = profit_per_item;
    }
}
