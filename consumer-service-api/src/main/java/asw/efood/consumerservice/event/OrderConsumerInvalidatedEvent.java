package asw.efood.consumerservice.event;

import asw.efood.common.event.DomainEvent;

public class OrderConsumerInvalidatedEvent implements DomainEvent {

    private String orderId;

    private String consumerId;

    public OrderConsumerInvalidatedEvent() {
    }

    public OrderConsumerInvalidatedEvent(String orderId, String consumerId) {
        this.orderId = orderId;
        this.consumerId = consumerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
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
