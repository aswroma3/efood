package asw.efood.orderhistoryservice;

import asw.efood.common.endpoint.event.CommonEventConfiguration;

import asw.efood.common.swagger.CommonSwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CommonSwaggerConfiguration.class, CommonEventConfiguration.class})
public class OrderHistoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderHistoryServiceApplication.class, args);
	}

}

