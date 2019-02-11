package asw.efood.restaurantservice.web;

public class CreateRestaurantResponse {

	private Long restaurantId;

	public CreateRestaurantResponse() {
	}

	public CreateRestaurantResponse(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
}

