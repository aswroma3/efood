package asw.efood.restaurantservice.event;

import asw.efood.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreatedEvent implements DomainEvent {

    private Long restaurantId;

    private String name;
    private String city;

    @Override
    public String toString() {
        return "RestaurantCreatedEvent{" +
                "restaurantId='" + restaurantId + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
