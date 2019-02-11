package asw.efood.orderservice.web;

public class CreateOrderResponse {

	private String orderId;

	private String consumerId;
	private String restaurantId;
	private String orderState;

	public CreateOrderResponse() {
	}

	public CreateOrderResponse(String orderId, String consumerId, String restaurantId, String orderState) {
		this.orderId = orderId;
		this.consumerId = consumerId;
		this.restaurantId = restaurantId;
		this.orderState = orderState;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
}

