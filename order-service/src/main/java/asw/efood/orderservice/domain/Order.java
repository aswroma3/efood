package asw.efood.orderservice.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="efoodorder")
public class Order {

	@Id
	private String id;

//	@Id
//	@GeneratedValue
//	private Long id;

	private String consumerId;

	private String restaurantId;

	@ElementCollection
	private List<OrderLineItem> orderLineItems;

	private OrderState state;

	public Order() {
	}

	public Order(String consumerId, String restaurantId, List<OrderLineItem> orderLineItems) {
		this.consumerId = consumerId;
		this.restaurantId = restaurantId;
		this.orderLineItems = orderLineItems;
		this.state = OrderState.PENDING;
		this.id = "ORDE-" + UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public static Order create(String customerId, String restaurantId, List<OrderLineItem> orderLineItems) {
		return new Order(customerId, restaurantId, orderLineItems);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Order order = (Order) o;

		return id != null ? id.equals(order.id) : order.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}

