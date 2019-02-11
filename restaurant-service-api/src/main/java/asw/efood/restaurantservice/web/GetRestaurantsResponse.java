package asw.efood.restaurantservice.web;

import java.util.List;

public class GetRestaurantsResponse {

	private List<GetRestaurantResponse> restaurants;

	public GetRestaurantsResponse() {
	}

	public GetRestaurantsResponse(List<GetRestaurantResponse> restaurants) {
		this.restaurants = restaurants;
	}

	public List<GetRestaurantResponse> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<GetRestaurantResponse> restaurants) {
		this.restaurants = restaurants;
	}
}

