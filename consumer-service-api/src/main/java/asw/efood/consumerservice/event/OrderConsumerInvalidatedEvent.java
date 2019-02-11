package asw.efood.consumerservice.event;

import asw.efood.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderConsumerInvalidatedEvent implements DomainEvent {

    private Long orderId;
    private Long consumerId;

    @Override
    public String toString() {
        return "OrderConsumerInvalidatedEvent{" +
                "orderId=" + orderId +
                ", consumerId=" + consumerId +
                '}';
    }
}
