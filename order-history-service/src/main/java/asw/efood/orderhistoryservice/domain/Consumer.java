package asw.efood.orderhistoryservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Consumer {

    @Id
    private Long consumerId;
    private String firstName;
    private String lastName;

    public Consumer() {
    }

    public Consumer(Long consumerId, String firstName, String lastName) {
        this.consumerId = consumerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Consumer consumer = (Consumer) o;

        return consumerId != null ? consumerId.equals(consumer.consumerId) : consumer.consumerId == null;
    }

    @Override
    public int hashCode() {
        return consumerId != null ? consumerId.hashCode() : 0;
    }
}
