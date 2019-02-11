package asw.efood.restaurantservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantsResponse {

	private List<GetRestaurantResponse> restaurants;

}

