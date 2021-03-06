package asw.efood.orderhistoryservice.web;

public class GetOrderResponse {

    private Long orderId;

    private String customerFirstName;
    private String customerLastName;

    private String restaurantName;
    private String restaurantCity;

    public GetOrderResponse() {
    }

    public GetOrderResponse(Long orderId, String customerFirstName, String customerLastName, String restaurantName, String restaurantCity) {
        this.orderId = orderId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.restaurantName = restaurantName;
        this.restaurantCity = restaurantCity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantCity() {
        return restaurantCity;
    }

    public void setRestaurantCity(String restaurantCity) {
        this.restaurantCity = restaurantCity;
    }
}
