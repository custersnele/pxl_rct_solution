package be.pxl.rct.shop;

public enum ShopType {

    BALLOON_STALL(250, 1.0, 0.7, ItemType.SOUVENIR),
    SOUVENIR_SHOP(350, 2.5, 1.5, ItemType.SOUVENIR),
    BURGER_BAR(300, 1.5, 1, ItemType.FOOD),
    // Cash Machine;
    DRINKS_STALL(225, 1.20, 0.9, ItemType.DRINKS),
    // First Aid Room;
    ICECREAM_STALL(250, 0.9, 0.5, ItemType.FOOD),
    HOT_DOG_STALL(290, 1.0, 0.5, ItemType.FOOD),
    //Information Kiosk;
    PIZZA_STALL(275, 1.6, 1, ItemType.FOOD);

    private double cost;
    private double pricePerItem;
    private double profitPerItem;
    private ItemType itemType;

    ShopType(double cost, double pricePerItem, double profitPerItem, ItemType itemType) {
        this.cost = cost;
        this.pricePerItem = pricePerItem;
        this.profitPerItem = profitPerItem;
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public double getProfitPerItem() {
        return profitPerItem;
    }

    public double getCost() {
        return cost;
    }

    public boolean hasItemType(ItemType itemType) {
        return this.itemType == itemType;
    }
}
