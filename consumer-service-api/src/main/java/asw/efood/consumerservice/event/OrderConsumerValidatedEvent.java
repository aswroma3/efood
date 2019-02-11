package asw.efood.consumerservice.event;

import asw.efood.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderConsumerValidatedEvent implements DomainEvent {

    private Long orderId;
    private Long consumerId;

    @Override
    public String toString() {
        return "OrderConsumerValidatedEvent{" +
                "orderId=" + orderId +
                ", consumerId=" + consumerId +
                '}';
    }
}
