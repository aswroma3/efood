package asw.efood.consumerservice.domain;

import asw.efood.common.event.DomainEventPublisher;
import asw.efood.consumerservice.ConsumerServiceChannel;
import asw.efood.consumerservice.event.ConsumerCreatedEvent;
import asw.efood.consumerservice.event.OrderConsumerInvalidatedEvent;
import asw.efood.consumerservice.event.OrderConsumerValidatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConsumerService {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	public Consumer create(String firstName, String lastName) {
		Consumer consumer = Consumer.create(firstName, lastName);
		consumer = consumerRepository.save(consumer);
		ConsumerCreatedEvent event = makeConsumerCreatedEvent(consumer);
		domainEventPublisher.publish(event, ConsumerServiceChannel.consumerServiceChannel);
		return consumer;
	}

	private ConsumerCreatedEvent makeConsumerCreatedEvent(Consumer consumer) {
		return new ConsumerCreatedEvent(consumer.getId(), consumer.getFirstName(), consumer.getLastName());
	}
	
	public Consumer findById(String consumerId) {
		return consumerRepository.findById(consumerId).orElse(null);
	}

	public List<Consumer> findAll() { return consumerRepository.findAll(); }

	public void validateOrderConsumer(String orderId, String consumerId) {
		Consumer consumer = findById(consumerId);
		if (consumer!=null) {
			OrderConsumerValidatedEvent event = new OrderConsumerValidatedEvent(orderId, consumerId);
			domainEventPublisher.publish(event, ConsumerServiceChannel.consumerServiceChannel);
		} else {
			OrderConsumerInvalidatedEvent event = new OrderConsumerInvalidatedEvent(orderId, consumerId);
			domainEventPublisher.publish(event, ConsumerServiceChannel.consumerServiceChannel);
		}
	}

}

