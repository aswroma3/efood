package asw.efood.consumerservice;

import asw.efood.common.endpoint.event.CommonEventConfiguration;
import asw.efood.common.swagger.CommonSwaggerConfiguration;

import asw.efood.consumerservice.grpc.GrpcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CommonSwaggerConfiguration.class, GrpcConfiguration.class, CommonEventConfiguration.class})
public class ConsumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerServiceApplication.class, args);
	}

}

