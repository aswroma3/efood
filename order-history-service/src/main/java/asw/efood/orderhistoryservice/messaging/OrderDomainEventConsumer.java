package asw.efood.orderhistoryservice.messaging;

import asw.efood.common.event.DomainEvent;
import asw.efood.orderhistoryservice.domain.Order;
import asw.efood.orderhistoryservice.domain.OrderHistoryService;
import asw.efood.orderservice.OrderServiceChannel;
import asw.efood.orderservice.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OrderDomainEventConsumer {

    private Logger logger = Logger.getLogger("OrderDomainEventConsumer");

    @Autowired
    private OrderHistoryService orderHistoryService;

    @KafkaListener(topics = {OrderServiceChannel.orderServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("ORDER DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        if (event.getClass().equals(OrderCreatedEvent.class)) {
            OrderCreatedEvent domainEvent = (OrderCreatedEvent) event;
            orderHistoryService.saveOrder(makeOrder(domainEvent));
            logger.info("ORDER SAVED");
        }
    }

    private Order makeOrder(OrderCreatedEvent domainEvent) {
        return new Order(domainEvent.getOrderId(), domainEvent.getConsumerId(), domainEvent.getRestaurantId());
    }

}
