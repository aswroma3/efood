package asw.efood.orderservice.web;

public class CreateOrderResponse {

	private Long orderId;

	private Long consumerId;
	private Long restaurantId;
	private String orderState;

	public CreateOrderResponse() {
	}

	public CreateOrderResponse(Long orderId, Long consumerId, Long restaurantId, String orderState) {
		this.orderId = orderId;
		this.consumerId = consumerId;
		this.restaurantId = restaurantId;
		this.orderState = orderState;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
}

