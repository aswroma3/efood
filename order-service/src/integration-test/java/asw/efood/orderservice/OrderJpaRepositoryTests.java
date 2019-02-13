package asw.efood.orderservice;

import asw.efood.orderservice.domain.Order;
import asw.efood.orderservice.domain.OrderLineItem;
import asw.efood.orderservice.domain.OrderRepository;
import asw.efood.orderservice.domain.OrderState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderJpaRepositoryTests {

    public static final Long ORDER_ID = 142L;
    public static final Long CONSUMER_ID = 42L;
    public static final Long RESTAURANT_ID = 242L;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void saveAndFindOrderTest() {

        long orderId = transactionTemplate.execute((ts) -> {
            List<OrderLineItem> orderLineItems = new ArrayList<>();
            orderLineItems.add(new OrderLineItem("Pizza", 2));
            Order order = new Order(CONSUMER_ID, RESTAURANT_ID, orderLineItems);
            order = orderRepository.save(order);
            return order.getId();
        });


        transactionTemplate.execute((ts) -> {
            Order order = orderRepository.findById(orderId).get();

            assertThat(order).isNotNull();
            assertThat(order.getConsumerId()).isEqualTo(CONSUMER_ID);
            assertThat(order.getRestaurantId()).isEqualTo(RESTAURANT_ID);
            assertThat(order.getOrderLineItems().size()).isEqualTo(1);
            assertThat(order.getState()).isEqualTo(OrderState.PENDING);
            return null;
        });

    }

}
