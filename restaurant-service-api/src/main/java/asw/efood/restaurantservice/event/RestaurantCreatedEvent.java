package asw.efood.restaurantservice.event;

import asw.efood.common.event.DomainEvent;

public class RestaurantCreatedEvent implements DomainEvent {

    private Long restaurantId;

    private String name;
    private String city;

    public RestaurantCreatedEvent() {
    }

    public RestaurantCreatedEvent(Long restaurantId, String name, String city) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.city = city;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "RestaurantCreatedEvent{" +
                "restaurantId='" + restaurantId + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
