package asw.efood.restaurantservice.web;

import java.util.List;
import java.util.stream.*;

public class GetRestaurantMenuResponse {

	private List<RestaurantMenuItem> menuItems;

	public GetRestaurantMenuResponse() {
	}

	public GetRestaurantMenuResponse(List<RestaurantMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
/*
	public GetRestaurantMenuResponse(RestaurantMenu menu) {
		menuItems =
				menu.getMenuItems()
					.stream()
					.map(x -> new RestaurantMenuItem(x.getItemId(), x.getName(), x.getPrice()))
					.collect(Collectors.toList());

	}
	*/

	public List<RestaurantMenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<RestaurantMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}

