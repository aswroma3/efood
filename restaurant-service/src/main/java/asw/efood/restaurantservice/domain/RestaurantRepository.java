package asw.efood.restaurantservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface RestaurantRepository extends CrudRepository<Restaurant, String> {

    Collection<Restaurant> findAllByCity(String city);


}


