package asw.efood.restaurantservice.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Restaurant {

	@Id
	private String id;
	
	private String name;
	private String city;

	@Embedded
	private RestaurantMenu menu;

	private Restaurant() {}
	
	public Restaurant(String name, String city) {
		this.name = name;
		this.city = city;
		this.id = "REST-" + UUID.randomUUID().toString();
	}

	public Restaurant(String name, String city, RestaurantMenu menu) {
		this(name, city);
		this.menu = menu;
	}

	public String getId() {
		return id; 
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Restaurant that = (Restaurant) o;

		return id != null ? id.equals(that.id) : that.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public RestaurantMenu getMenu() {
		return menu;
	}

	public void setMenu(RestaurantMenu menu) {
		this.menu = menu;
	}

	public static Restaurant create(String name, String city, RestaurantMenu menu) {
		return new Restaurant(name, city, menu);
	}

	public static Restaurant create(String name, String city) {
		return new Restaurant(name, city);
	}

}

