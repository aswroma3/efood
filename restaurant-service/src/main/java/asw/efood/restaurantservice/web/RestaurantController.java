package asw.efood.restaurantservice.web;

import asw.efood.restaurantservice.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping(path="/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	/** Crea un nuovo ristorante. */
	@RequestMapping(path="/", method=RequestMethod.POST)
	public CreateRestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest request) {
		Restaurant restaurant = restaurantService.create(request.getName(), request.getCity());
		return makeCreateRestaurantResponse(restaurant);
	}

	/* Crea la risposta a partire dal ristorante. */
	private CreateRestaurantResponse makeCreateRestaurantResponse(Restaurant restaurant) {
		return new CreateRestaurantResponse(restaurant.getId());
	}

	/** Crea un nuovo menu per il ristorante con restaurantId. */
	@RequestMapping(path="/{restaurantId}/menu", method=RequestMethod.POST)
	public CreateRestaurantMenuResponse createRestaurantMenu(@PathVariable Long restaurantId, @RequestBody CreateRestaurantMenuRequest request) {
		List<MenuItem> menuItems = getMenuItems(request);
		Restaurant restaurant = restaurantService.createMenu(restaurantId, menuItems);
		return makeCreateRestaurantMenuResponse(restaurant);
	}

	/* Estrae dalla richiesta la lista degli item. */
	private List<MenuItem> getMenuItems(CreateRestaurantMenuRequest request) {
		return request.getMenuItems()
				.stream()
				.map(x -> new MenuItem(x.getItemId(), x.getName(), x.getPrice()))
				.collect(Collectors.toList());
	}

	/* Crea la risposta a partire dal ristorante. */
	private CreateRestaurantMenuResponse makeCreateRestaurantMenuResponse(Restaurant restaurant) {
		return new CreateRestaurantMenuResponse(restaurant.getId());
	}

	/** Trova il ristorante con restaurantId. */
	@RequestMapping(path="/{restaurantId}", method=RequestMethod.GET)
	public ResponseEntity<GetRestaurantResponse> getRestaurant(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		if (restaurant!=null) {
			return new ResponseEntity<>(makeGetRestaurantResponse(restaurant), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* Crea la risposta a partire dal ristorante. */
	private GetRestaurantResponse makeGetRestaurantResponse(Restaurant restaurant) {
		return new GetRestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getCity());
	}


	/** Trova il menu del ristorante con restaurantId. */
	@RequestMapping(path="/{restaurantId}/menu", method=RequestMethod.GET)
	public ResponseEntity<GetRestaurantMenuResponse> getRestaurantMenu(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findById(restaurantId);
		if (restaurant!=null) {
			return new ResponseEntity<>(makeGetRestaurantMenuResponse(restaurant), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* Crea la risposta a partire dal ristorante. */
	private GetRestaurantMenuResponse makeGetRestaurantMenuResponse(Restaurant restaurant) {
		List<RestaurantMenuItem> menuItems =
				restaurant.getMenu().getMenuItems()
						.stream()
						.map(x -> new RestaurantMenuItem(x.getId(), x.getName(), x.getPrice()))
						.collect(Collectors.toList());
		return new GetRestaurantMenuResponse(menuItems);
	}

//	/** Trova i ristoranti in city. */
//	@RequestMapping(path="/", method=RequestMethod.GET)
//	public ResponseEntity<Collection<GetRestaurantResponse>> getRestaurantsByCity(@RequestParam("city") String city) {
//		Collection<Restaurant> restaurants = restaurantService.findByCity(city);
//		/* in effetti, restituisce sempre una lista non nulla */
//		if (restaurants!=null) {
//			Collection<GetRestaurantResponse> response =
//					restaurants
//							.stream()
//							.map(r -> new GetRestaurantResponse(r.getItemId(), r.getName(), r.getCity()))
//							.collect(Collectors.toList());
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			/* dunque non arriva mai qui */
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	/** Trova i ristoranti in city. */
	@RequestMapping(path="/", method=RequestMethod.GET)
	public ResponseEntity<GetRestaurantsResponse> getRestaurantsByCity(@RequestParam("city") String city) {
		Collection<Restaurant> restaurants = restaurantService.findByCity(city);
		/* in effetti, restituisce sempre una lista non nulla */
		if (restaurants!=null) {
			return new ResponseEntity<>(makeGetRestaurantsResponse(restaurants), HttpStatus.OK);
		} else {
			/* dunque non arriva mai qui */
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* Crea la risposta a partire dalla lista dei ristoranti. */
	private GetRestaurantsResponse makeGetRestaurantsResponse(Collection<Restaurant> restaurants) {
		List<GetRestaurantResponse> responses =
				restaurants
						.stream()
						.map(r -> makeGetRestaurantResponse(r))
						.collect(Collectors.toList());
		return new GetRestaurantsResponse(responses);
	}

}

