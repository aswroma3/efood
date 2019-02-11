package asw.efood.eventlogger.kafka;

import asw.efood.common.event.DomainEvent;
import asw.efood.consumerservice.ConsumerServiceChannel;
import asw.efood.orderservice.OrderServiceChannel;
import asw.efood.restaurantservice.RestaurantServiceChannel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DomainEventLoggerImpl {

    // public static final String EFOOD_KAFKA_TOPIC = "efood";

    private Logger logger = Logger.getLogger("DomainEventLoggerImpl");

    @KafkaListener(topics =
            {ConsumerServiceChannel.consumerServiceChannel, RestaurantServiceChannel.restaurantServiceChannel, OrderServiceChannel.orderServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> event) throws Exception {
        logger.info("DOMAIN EVENT LOGGER: " + event.toString());
        // logger.info("DOMAIN EVENT LOGGER: event type: " + event.value().getClass().toString());
    }

}
