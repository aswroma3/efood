package asw.efood.orderservice.web;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

	private Long consumerId;
	private Long restaurantId;
	private List<LineItem> lineItems;

}

