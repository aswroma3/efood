package asw.efood.orderservice.web;

import asw.efood.orderservice.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(path="/", method=RequestMethod.POST)
	public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
		List<OrderLineItem> orderLineItems = getOrderLineItems(request);
		Order order = orderService.create(request.getConsumerId(), request.getRestaurantId(), orderLineItems);
		return makeCreateOrderResponse(order);
	}

	private List<OrderLineItem> getOrderLineItems(CreateOrderRequest request) {
		return request.getLineItems()
			.stream()
			.map(x -> new OrderLineItem(x.getMenuItemId(), x.getQuantity()))
			.collect(Collectors.toList());
	}


	private CreateOrderResponse makeCreateOrderResponse(Order order) {
		return new CreateOrderResponse(order.getId(), order.getConsumerId(), order.getRestaurantId(), order.getState().toString());
	}

	@RequestMapping(path="/{orderId}", method=RequestMethod.GET)
	public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Long orderId) {
		Order order = orderService.findById(orderId);
		if (order!=null) {
			return new ResponseEntity<>(makeGetOrderResponse(order), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private GetOrderResponse makeGetOrderResponse(Order order) {
		List<LineItem> lineItems =
				order.getOrderLineItems()
						.stream()
						.map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
						.collect(Collectors.toList());
		return new GetOrderResponse(order.getId(), order.getConsumerId(), order.getRestaurantId(), lineItems, order.getState().toString());

	}

}

