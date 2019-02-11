package asw.efood.orderservice.adapter.consumerservice.web;

import asw.efood.consumerservice.web.GetConsumerResponse;
import asw.efood.orderservice.domain.ConsumerServiceAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class ConsumerServiceAdapterRestTemplateImpl implements ConsumerServiceAdapter {

    @Value("${efood.consumerservice.uri}")
    private String consumerServiceUri;

    private Logger logger = Logger.getLogger("ConsumerServiceAdapter");

    public boolean validateConsumer(Long consumerId) {
        RestTemplate restTemplate = new RestTemplate();
        String consumerUrl = consumerServiceUri + "/consumers/{consumerId}";
        logger.info("Looking for " + consumerUrl + " with " + consumerId);
        GetConsumerResponse consumer = null;
        try {
            ResponseEntity<GetConsumerResponse> entity = restTemplate.getForEntity(consumerUrl, GetConsumerResponse.class, consumerId);
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                consumer = entity.getBody();
                logger.info("Consumer found: " + consumer.getFirstName() + " " + consumer.getLastName());
            } else {
                logger.info("Consumer not found");
            }
        } catch(RestClientException e) {
            logger.info("Consumer not found, with exception " + e.getMessage());
        }
        return consumer!=null;
    }
}
