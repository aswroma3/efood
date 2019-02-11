package asw.efood.orderservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {

	private Long orderId;

	private Long consumerId;
	private Long restaurantId;
	private String orderState;

}

