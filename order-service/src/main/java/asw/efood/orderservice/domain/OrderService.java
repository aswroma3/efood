package asw.efood.orderservice.domain;

import asw.efood.common.event.DomainEventPublisher;
import asw.efood.orderservice.OrderServiceChannel;
import asw.efood.orderservice.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ConsumerServiceAdapter consumerService;

	@Autowired
	private RestaurantServiceAdapter restaurantService;

	@Autowired
	private DomainEventPublisher domainEventPublisher;

	/* Creazione di un nuovo ordine. */
	public Order create(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems) {
		return createAsincrona(consumerId, restaurantId, orderLineItems);
		// return createSincrona(consumerId, restaurantId, orderLineItems);
	}

	/* Creazione di un nuovo ordine. Versione basata su eventi. */
	public Order createAsincrona(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems) {
		/* crea e salva l'ordine */
		Order order = Order.create(consumerId, restaurantId, orderLineItems);
		order = orderRepository.save(order);
		/* crea l'evento corrispondente */
		OrderCreatedEvent event = makeOrderCreatedEvent(order);
		domainEventPublisher.publish(event, OrderServiceChannel.orderServiceChannel);
		return order;
	}

	private OrderCreatedEvent makeOrderCreatedEvent(Order order) {
		List<LineItem> lineItems = order.getOrderLineItems()
				.stream()
				.map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
				.collect(Collectors.toList());
		return new OrderCreatedEvent(order.getId(), order.getConsumerId(), order.getRestaurantId(), lineItems);
	}

	/* Creazione di un nuovo ordine. Versione sincrona (senza eventi) e sequenziale. */
	public Order createSincrona(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems) {
		Order order = Order.create(consumerId, restaurantId, orderLineItems);
		order = orderRepository.save(order);
		boolean consumerOk = consumerService.validateConsumer(order.getConsumerId());
		boolean restaurantOk = restaurantService.validateRestaurant(order.getRestaurantId());
		/* in effetti, avendo eseguito entrambe le validazioni in modo sincrono,
		 * i possibili stati finali dovrebbero essere solo APPROVED oppure INVALID. */
		if (consumerOk && restaurantOk) {
			order.setState(OrderState.APPROVED);
		} else if (consumerOk) {
			order.setState(OrderState.CONSUMER_APPROVED);
		} else if (restaurantOk) {
			order.setState(OrderState.DETAILS_APPROVED);
		} else {
			order.setState(OrderState.INVALID);
		}
		order = orderRepository.save(order);
		return order;
	}

	public Order findById(Long orderId) {
		return orderRepository.findById(orderId).orElse(null);
	}

	public Order confirmConsumer(Long orderId, Long consumerId) {
		Order order = findById(orderId);
		if (order.getState().equals(OrderState.PENDING)) {
			order.setState(OrderState.CONSUMER_APPROVED);
			order = orderRepository.save(order);
		} else if (order.getState().equals(OrderState.DETAILS_APPROVED)) {
			order.setState(OrderState.APPROVED);
			order = orderRepository.save(order);
		}
		return order;
	}

	public Order invalidateConsumer(Long orderId, Long consumerId) {
		Order order = findById(orderId);
		order.setState(OrderState.INVALID);
		order = orderRepository.save(order);
		return order;
	}

}

