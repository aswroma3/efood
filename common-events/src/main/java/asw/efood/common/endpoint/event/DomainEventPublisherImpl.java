package asw.efood.common.endpoint.event;

import asw.efood.common.event.DomainEvent;
import asw.efood.common.event.DomainEventListener;
import asw.efood.common.event.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.logging.Logger;

public class DomainEventPublisherImpl implements DomainEventPublisher {

//    public static final String EFOOD_KAFKA_TOPIC = "efood";

    private Logger logger = Logger.getLogger("DomainEventPublisherImpl");

    @Autowired
    private KafkaTemplate<String, DomainEvent> template;

    @Override
    public void subscribe(DomainEventListener listener) {
        // TODO
    }

    @Override
    public void publish(DomainEvent event, String channel) {
        logger.info("PUBLISHING DOMAIN EVENT: " + event.toString() + " ON CHANNEL: " + channel);
        template.send(channel, event);
        // template.flush();
    }

//    @KafkaListener(topics = EFOOD_KAFKA_TOPIC)
//    public void listen(ConsumerRecord<?, ?> event) throws Exception {
//        logger.info("DOMAIN EVENT LOGGER: " + event.toString());
//    }

}
