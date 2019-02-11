package asw.efood.orderhistoryservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Restaurant {

    @Id
    private String restaurantId;
    private String name;
    private String city;

    public Restaurant() {
    }

    public Restaurant(String restaurantId, String name, String city) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.city = city;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return restaurantId != null ? restaurantId.equals(that.restaurantId) : that.restaurantId == null;
    }

    @Override
    public int hashCode() {
        return restaurantId != null ? restaurantId.hashCode() : 0;
    }
}
