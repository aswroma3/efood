package asw.efood.restaurantservice.web;

import lombok.Data;

import java.util.*;

@Data
public class CreateRestaurantMenuRequest {

	private List<RestaurantMenuItem> menuItems;

}

