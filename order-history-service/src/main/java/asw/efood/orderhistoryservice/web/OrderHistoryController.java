package asw.efood.orderhistoryservice.web;

import asw.efood.orderhistoryservice.domain.Consumer;
import asw.efood.orderhistoryservice.domain.Order;
import asw.efood.orderhistoryservice.domain.OrderHistoryService;
import asw.efood.orderhistoryservice.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/orders")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @RequestMapping(path="/{orderId}", method= RequestMethod.GET)
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderId) {
        try {
            Order order = orderHistoryService.findOrder(orderId);
            Consumer consumer = orderHistoryService.findConsumer(order.getConsumerId());
            Restaurant restaurant = orderHistoryService.findRestaurant(order.getRestaurantId());
            return new ResponseEntity<>(makeGetOrderResponse(order, consumer, restaurant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetOrderResponse makeGetOrderResponse(Order order, Consumer consumer, Restaurant restaurant) {
        return new GetOrderResponse(order.getOrderId(), consumer.getFirstName(), consumer.getLastName(), restaurant.getName(), restaurant.getCity());
    }

}
