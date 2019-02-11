package asw.efood.orderservice.web;

import java.util.List;

public class GetOrderResponse {

	private String orderId;

	private String consumerId;

	private String restaurantId;

	private List<LineItem> lineItems;

	private String orderState;

	public GetOrderResponse() {
	}

	public GetOrderResponse(String orderId, String consumerId, String restaurantId, List<LineItem> lineItems, String orderState) {
		this.orderId = orderId;
		this.consumerId = consumerId;
		this.restaurantId = restaurantId;
		this.lineItems = lineItems;
		this.orderState = orderState;
	}

//	public GetOrderResponse(Order order) {
//		this.orderId = order.getId();
//		this.consumerId = order.getConsumerId();
//		this.restaurantId = order.getRestaurantId();
//		this.lineItems =
//				order.getLineItems()
//					.stream()
//					.map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
//					.collect(Collectors.toList());
//		this.state = order.getState();
//	}

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

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
}

