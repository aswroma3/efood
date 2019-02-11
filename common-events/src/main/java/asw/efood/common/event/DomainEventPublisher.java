package asw.efood.common.event;

public interface DomainEventPublisher {

    public void subscribe(DomainEventListener listener);

    public void publish(DomainEvent event, String channel);
}
