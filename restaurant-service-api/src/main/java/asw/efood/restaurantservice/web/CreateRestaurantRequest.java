package asw.efood.restaurantservice.web;

public class CreateRestaurantRequest {

	private String name;

	private String city;

	public CreateRestaurantRequest() {
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

