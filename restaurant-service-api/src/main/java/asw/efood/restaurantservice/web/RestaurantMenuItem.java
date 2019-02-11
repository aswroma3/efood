package asw.efood.restaurantservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMenuItem {

    private String itemId;
    private String name;
    private double price;
}
