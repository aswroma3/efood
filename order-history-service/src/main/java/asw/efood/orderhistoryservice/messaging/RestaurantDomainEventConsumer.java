package asw.efood.orderhistoryservice.messaging;

import asw.efood.common.event.DomainEvent;
import asw.efood.orderhistoryservice.domain.OrderHistoryService;
import asw.efood.orderhistoryservice.domain.Restaurant;
import asw.efood.restaurantservice.RestaurantServiceChannel;
import asw.efood.restaurantservice.event.RestaurantCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RestaurantDomainEventConsumer {

    private Logger logger = Logger.getLogger("RestaurantDomainEventConsumer");

    @Autowired
    private OrderHistoryService orderHistoryService;

    @KafkaListener(topics = {RestaurantServiceChannel.restaurantServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("RESTAURANT DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        if (event.getClass().equals(RestaurantCreatedEvent.class)) {
            RestaurantCreatedEvent domainEvent = (RestaurantCreatedEvent) event;
            orderHistoryService.saveRestaurant(makeRestaurant(domainEvent));
            logger.info("RESTAURANT SAVED");
        }
    }

    private Restaurant makeRestaurant(RestaurantCreatedEvent domainEvent) {
        return new Restaurant(domainEvent.getRestaurantId(), domainEvent.getName(), domainEvent.getCity());
    }

}
