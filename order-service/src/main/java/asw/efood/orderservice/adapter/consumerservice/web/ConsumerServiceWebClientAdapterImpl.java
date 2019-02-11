package asw.efood.orderservice.adapter.consumerservice.web;

import asw.efood.consumerservice.web.GetConsumerResponse;
import asw.efood.orderservice.domain.ConsumerServiceAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
// @Primary
public class ConsumerServiceWebClientAdapterImpl implements ConsumerServiceAdapter {

    @Value("${efood.consumerservice.uri}")
    private String consumerServiceUri;

    private WebClient webClient;

    private Logger logger = Logger.getLogger("ConsumerServiceAdapter");

    public ConsumerServiceWebClientAdapterImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(consumerServiceUri).build();
    }

    public boolean validateConsumer(Long consumerId) {
        String consumerUrl = consumerServiceUri + "/consumers/{consumerId}";
        logger.info("Looking for " + consumerUrl + " with " + consumerId);
        GetConsumerResponse consumer = null;
        Mono<GetConsumerResponse> response = webClient
                .get()
                .uri(consumerUrl, consumerId)
                .retrieve()
                .bodyToMono(GetConsumerResponse.class);
        try {
            consumer = response.block();
            if (consumer != null) {
                logger.info("Consumer found: " + consumer.getFirstName() + " " + consumer.getLastName());
            } else {
                logger.info("Consumer not found");
            }
        } catch (WebClientException e) {
            logger.info("Consumer not found, with exception " + e.getMessage());
        }
        return consumer!=null;
    }
}
