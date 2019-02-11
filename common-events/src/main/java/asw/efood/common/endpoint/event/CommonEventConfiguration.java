package asw.efood.common.endpoint.event;

import asw.efood.common.event.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonEventConfiguration {

    @Bean
    public DomainEventPublisher domainEventPublisher() {
        return new DomainEventPublisherImpl();
    }
}
