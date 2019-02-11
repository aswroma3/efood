package asw.efood.orderservice.adapter.restaurantService.web;

import asw.efood.orderservice.domain.RestaurantServiceAdapter;
import asw.efood.restaurantservice.web.GetRestaurantResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
@Primary
public class RestaurantServicWebClientAdapterImpl implements RestaurantServiceAdapter {

    @Value("${efood.restaurantservice.uri}")
    private String restaurantServiceUri;

    private WebClient webClient;

    private Logger logger = Logger.getLogger("RestaurantServiceAdapter");

    public RestaurantServicWebClientAdapterImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(restaurantServiceUri).build();
    }

    public boolean validateRestaurant(Long restaurantId) {
        String restaurantUrl = restaurantServiceUri + "/restaurants/{restaurantId}";
        logger.info("Looking for " + restaurantUrl + " with " + restaurantId);
        GetRestaurantResponse restaurant = null;
        Mono<GetRestaurantResponse> response = webClient
                .get()
                .uri(restaurantUrl, restaurantId)
                .retrieve()
                .bodyToMono(GetRestaurantResponse.class);
        try {
            restaurant = response.block();
            if (restaurant != null) {
                logger.info("Restaurant found: " + restaurant.getName() + " " + restaurant.getCity());
            } else {
                logger.info("Restaurant not found");
            }
        } catch (WebClientException e) {
            logger.info("Restaurant not found, with exception " + e.getMessage());
        }
        return restaurant!=null;
    }

}
