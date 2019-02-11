package asw.efood.consumerservice.messaging;

import asw.efood.common.event.DomainEvent;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.orderservice.OrderServiceChannel;
import asw.efood.orderservice.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OrderDomainEventConsumer {

//    public static final String EFOOD_KAFKA_TOPIC = "efood";

    private Logger logger = Logger.getLogger("OrderDomainEventConsumer");

    @Autowired
    private ConsumerService consumerService;

    @KafkaListener(topics = OrderServiceChannel.orderServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("ORDER DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        if (event.getClass().equals(OrderCreatedEvent.class)) {
            OrderCreatedEvent domainEvent = (OrderCreatedEvent) event;
            consumerService.validateOrderConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
        }
    }

}
