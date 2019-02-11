package asw.efood.restaurantservice.web;

import java.util.*;

public class CreateRestaurantMenuRequest {

	private List<RestaurantMenuItem> menuItems;

	public CreateRestaurantMenuRequest() {
	}

	public List<RestaurantMenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<RestaurantMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}

