package asw.efood.consumerservice.grpc;

import asw.efood.consumerservice.domain.ConsumerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class GrpcConfiguration {
    @Bean
    public ConsumerServiceServer consumerServiceServer(ConsumerService consumerService) {
        return new ConsumerServiceServer(consumerService);
    }

}
