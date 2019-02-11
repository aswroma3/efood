package asw.efood.restaurantservice.web;

public class CreateRestaurantMenuResponse {

	private String restaurantId;

	public CreateRestaurantMenuResponse() {
	}

	public CreateRestaurantMenuResponse(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
}

