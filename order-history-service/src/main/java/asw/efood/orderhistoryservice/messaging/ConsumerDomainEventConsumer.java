package asw.efood.orderhistoryservice.messaging;

import asw.efood.common.event.DomainEvent;
import asw.efood.consumerservice.ConsumerServiceChannel;
import asw.efood.consumerservice.event.ConsumerCreatedEvent;
import asw.efood.orderhistoryservice.domain.Consumer;
import asw.efood.orderhistoryservice.domain.OrderHistoryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ConsumerDomainEventConsumer {

    private Logger logger = Logger.getLogger("ConsumerDomainEventConsumer");

    @Autowired
    private OrderHistoryService orderHistoryService;

    @KafkaListener(topics = {ConsumerServiceChannel.consumerServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("CONSUMER DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        if (event.getClass().equals(ConsumerCreatedEvent.class)) {
            ConsumerCreatedEvent domainEvent = (ConsumerCreatedEvent) event;
            orderHistoryService.saveConsumer(makeConsumer(domainEvent));
            logger.info("CONSUMER SAVED");
        }
        // logger.info("DOMAIN EVENT LOGGER: event type: " + event.value().getClass().toString());
    }

    private Consumer makeConsumer(ConsumerCreatedEvent domainEvent) {
        return new Consumer(domainEvent.getConsumerId(), domainEvent.getFirstName(), domainEvent.getLastName());
    }

}
