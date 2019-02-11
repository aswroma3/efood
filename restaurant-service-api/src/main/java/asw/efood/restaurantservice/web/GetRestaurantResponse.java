package asw.efood.restaurantservice.web;

public class GetRestaurantResponse {

	private Long restaurantId;
	
	private String name;
	private String city;

	public GetRestaurantResponse() {
	}

	public GetRestaurantResponse(Long restaurantId, String name, String city) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.city = city;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}

