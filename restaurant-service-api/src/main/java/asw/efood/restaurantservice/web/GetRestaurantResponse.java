package asw.efood.restaurantservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantResponse {

	private Long restaurantId;
	
	private String name;
	private String city;

}

