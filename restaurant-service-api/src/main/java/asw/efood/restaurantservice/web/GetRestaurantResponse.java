package asw.efood.restaurantservice.web;

public class GetRestaurantResponse {

	private String restaurantId;
	
	private String name;
	private String city;

	public GetRestaurantResponse() {
	}

	public GetRestaurantResponse(String restaurantId, String name, String city) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.city = city;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
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

