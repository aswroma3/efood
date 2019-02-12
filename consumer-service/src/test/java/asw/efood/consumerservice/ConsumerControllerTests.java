package asw.efood.consumerservice;

import asw.efood.common.event.DomainEventPublisher;
import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerRepository;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.consumerservice.event.ConsumerCreatedEvent;
import asw.efood.consumerservice.web.ConsumerController;
import asw.efood.consumerservice.web.CreateConsumerRequest;
import asw.efood.consumerservice.web.CreateConsumerResponse;
import asw.efood.consumerservice.web.GetConsumerResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

/* Test unitari per un controller, basato sui mock dei servizi di dominio utilizzati. */
@SpringBootTest
public class ConsumerControllerTests {

	/* il servizio da testare, in cui iniettare i successivi mock */
	@InjectMocks
	private ConsumerController consumerController;
	/* le dipendenze del servizio da testare */
	@Mock
	private ConsumerService consumerService;

	private static final Long CONSUMER_ID = 42L;
	private static final String CONSUMER_FIRST_NAME = "Mario";
	private static final String CONSUMER_LAST_NAME = "Rossi";
	private static final Consumer CONSUMER = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);

	private static final Long NONEXISTING_CONSUMER_ID = 17L;


	@Before
	public void setup() {
		/* inizializza i mock ed il servizio da testare */
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void postConsumerTest() {
		/* verifica dell'operazione POST /consumers */

		/* configura ConsumerService.create per creare il consumatore */
		when(consumerService.create(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME))
				.then(invocation -> {
					Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
					consumer.setId(CONSUMER_ID);
					return consumer;
				});

		/* invoca l'operazione POST /consumers */
		CreateConsumerRequest request = new CreateConsumerRequest();
		request.setFirstName(CONSUMER_FIRST_NAME);
		request.setLastName(CONSUMER_LAST_NAME);
		CreateConsumerResponse response = consumerController.createConsumer(request);

		/* verifica che il servizio è stato invocato  */
		verify(consumerService).create(same(CONSUMER_FIRST_NAME), same(CONSUMER_LAST_NAME));

		/* verifica la risposta  */
		/* TODO: notare che non ho modo di verificare lo stato HTTP */
		assertThat(response.getConsumerId()).isEqualTo(CONSUMER_ID);
	}


	@Test
	public void getConsumerTest() {
		/* verifica dell'operazione GET /consumers/{consumerId} */

		/* configura ConsumerService.findById per trovare il consumatore */
		when(consumerService.findById(CONSUMER_ID))
				.then(invocation -> {
					Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
					consumer.setId(CONSUMER_ID);
					return consumer;
				});

		/* invoca l'operazione GET /consumers/{consumerId} */
		ResponseEntity<GetConsumerResponse> responseEntity = consumerController.getConsumer(CONSUMER_ID);

		/* verifica che il servizio è stato invocato  */
		verify(consumerService).findById(same(CONSUMER_ID));

		/* verifica la risposta  */
		/* TODO: ha senso verificare lo stato HTTP? */
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		GetConsumerResponse response = responseEntity.getBody();
		assertThat(response.getConsumerId()).isEqualTo(CONSUMER_ID);
		assertThat(response.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME);
		assertThat(response.getLastName()).isEqualTo(CONSUMER_LAST_NAME);
	}

	@Test
	public void getConsumerNotFoundTest() {
		/* verifica dell'operazione GET /consumers/{consumerId} nel caso in cui il consumatore non venga trovato */

		/* configura ConsumerService.findById per trovare il consumatore */
		when(consumerService.findById(NONEXISTING_CONSUMER_ID))
				.then(invocation -> {
					return null;
				});

		/* invoca l'operazione GET /consumers/{consumerId} */
		ResponseEntity<GetConsumerResponse> responseEntity = consumerController.getConsumer(NONEXISTING_CONSUMER_ID);

		/* verifica che il servizio è stato invocato  */
		verify(consumerService).findById(same(NONEXISTING_CONSUMER_ID));

		/* verifica la risposta  */
		/* TODO: ha senso verificare lo stato HTTP? */
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		GetConsumerResponse response = responseEntity.getBody();
		assertThat(response).isNull();
	}

}

