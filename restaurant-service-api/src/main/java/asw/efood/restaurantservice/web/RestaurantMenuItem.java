package asw.efood.restaurantservice.web;

public class RestaurantMenuItem {

    private String itemId;
    private String name;
    private double price;

    public RestaurantMenuItem() {}

    public RestaurantMenuItem(String itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
