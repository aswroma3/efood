package asw.efood.restaurantservice.web;

public class CreateRestaurantMenuResponse {

	private Long restaurantId;

	public CreateRestaurantMenuResponse() {
	}

	public CreateRestaurantMenuResponse(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
}

