package asw.efood.consumerservice.event;

import asw.efood.common.event.DomainEvent;

public class OrderConsumerInvalidatedEvent implements DomainEvent {

    private Long orderId;

    private Long consumerId;

    public OrderConsumerInvalidatedEvent() {
    }

    public OrderConsumerInvalidatedEvent(Long orderId, Long consumerId) {
        this.orderId = orderId;
        this.consumerId = consumerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public String toString() {
        return "OrderConsumerInvalidatedEvent{" +
                "orderId=" + orderId +
                ", consumerId=" + consumerId +
                '}';
    }
}
