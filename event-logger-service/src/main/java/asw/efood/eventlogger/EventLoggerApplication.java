package asw.efood.eventlogger;

import asw.efood.common.endpoint.event.CommonEventConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import({CommonEventConfiguration.class})
public class EventLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventLoggerApplication.class, args);
	}

}

