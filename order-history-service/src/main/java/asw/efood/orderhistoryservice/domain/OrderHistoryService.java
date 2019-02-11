package asw.efood.orderhistoryservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHistoryService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    /* Salva un consumatore. */
    public Consumer saveConsumer(Consumer consumer) {
        consumer = consumerRepository.save(consumer);
        return consumer;
    }

    /* Salva un ristorante. */
    public Restaurant saveRestaurant(Restaurant restaurant) {
        restaurant = restaurantRepository.save(restaurant);
        return restaurant;
    }

    /* Salva un ordine. */
    public Order saveOrder(Order order) {
        order = orderRepository.save(order);
        return order;
    }

    /* Cerca un consumatore. */
    public Consumer findConsumer(String consumerId) {
        return consumerRepository.findById(consumerId).orElse(null);
    }

    /* Cerca un ristorante. */
    public Restaurant findRestaurant(String restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    /* Cerca un ordine. */
    public Order findOrder(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }


}
