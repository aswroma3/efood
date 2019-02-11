package asw.efood.restaurantservice.domain;

import asw.efood.common.event.DomainEventPublisher;
import asw.efood.restaurantservice.RestaurantServiceChannel;
import asw.efood.restaurantservice.event.RestaurantCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	public Restaurant create(String name, String city) {
		Restaurant restaurant = Restaurant.create(name, city);
		restaurant = restaurantRepository.save(restaurant);
		RestaurantCreatedEvent event = makeRestaurantCreatedEvent(restaurant);
		domainEventPublisher.publish(event, RestaurantServiceChannel.restaurantServiceChannel);
		return restaurant;
	}

	private RestaurantCreatedEvent makeRestaurantCreatedEvent(Restaurant restaurant) {
		return new RestaurantCreatedEvent(restaurant.getId(), restaurant.getName(), restaurant.getCity());
	}

	public Restaurant createMenu(Long restaurantId, List<MenuItem> menuItems) {
		RestaurantMenu menu = new RestaurantMenu(menuItems);

		Restaurant restaurant = findById(restaurantId);
		restaurant.setMenu(menu);
		restaurant = restaurantRepository.save(restaurant);
		return restaurant;
	}

	public Restaurant findById(Long restaurantId) {
		return restaurantRepository.findById(restaurantId).orElse(null);
	}

	public Collection<Restaurant> findByCity(String city) {
		return restaurantRepository.findAllByCity(city);
	}

}

