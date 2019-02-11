package asw.efood.consumerservice.event;

import asw.efood.common.event.DomainEvent;

public class ConsumerCreatedEvent implements DomainEvent {

    private String consumerId;

    private String firstName;
    private String lastName;

    public ConsumerCreatedEvent() {
    }

    public ConsumerCreatedEvent(String consumerId, String firstName, String lastName) {
        this.consumerId = consumerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ConsumerCreatedEvent{" +
                "consumerId='" + consumerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
