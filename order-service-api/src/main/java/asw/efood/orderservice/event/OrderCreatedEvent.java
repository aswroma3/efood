package asw.efood.orderservice.event;

import asw.efood.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements DomainEvent {

    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private List<LineItem> lineItems;

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", consumerId=" + consumerId +
                ", restaurantId=" + restaurantId +
                ", lineItems=" + lineItems +
                '}';
    }
}
