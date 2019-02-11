package asw.efood.consumerservice.event;

import asw.efood.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerCreatedEvent implements DomainEvent {

    private Long consumerId;

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "ConsumerCreatedEvent{" +
                "consumerId='" + consumerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
