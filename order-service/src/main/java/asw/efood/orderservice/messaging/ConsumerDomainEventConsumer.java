package asw.efood.orderservice.messaging;

import asw.efood.common.event.DomainEvent;
import asw.efood.consumerservice.ConsumerServiceChannel;
import asw.efood.consumerservice.event.OrderConsumerInvalidatedEvent;
import asw.efood.consumerservice.event.OrderConsumerValidatedEvent;
import asw.efood.orderservice.domain.OrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ConsumerDomainEventConsumer {

//    public static final String EFOOD_KAFKA_TOPIC = "efood";

    private Logger logger = Logger.getLogger("ConsumerDomainEventConsumer");

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = ConsumerServiceChannel.consumerServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("CONSUMER DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        if (event.getClass().equals(OrderConsumerValidatedEvent.class)) {
            OrderConsumerValidatedEvent domainEvent = (OrderConsumerValidatedEvent) event;
            orderService.confirmConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
        } else if (event.getClass().equals(OrderConsumerInvalidatedEvent.class)) {
            OrderConsumerInvalidatedEvent domainEvent = (OrderConsumerInvalidatedEvent) event;
            orderService.invalidateConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
        }
    }


}
