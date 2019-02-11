package asw.efood.orderservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {

	private Long orderId;
	private Long consumerId;
	private Long restaurantId;
	private List<LineItem> lineItems;
	private String orderState;

}

