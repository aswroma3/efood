package asw.efood.orderservice.event;

import asw.efood.common.event.DomainEvent;

import java.util.List;

public class OrderCreatedEvent implements DomainEvent {

    private String orderId;

    private String consumerId;

    private String restaurantId;

    private List<LineItem> lineItems;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String consumerId, String restaurantId, List<LineItem> lineItems) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.lineItems = lineItems;
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

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

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
