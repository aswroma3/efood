package asw.efood.restaurantservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantMenuResponse {

	private List<RestaurantMenuItem> menuItems;

}

