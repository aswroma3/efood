package asw.efood.consumerservice;

import asw.efood.common.event.DomainEventPublisher;
import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerRepository;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.consumerservice.event.ConsumerCreatedEvent;
import asw.efood.consumerservice.event.OrderConsumerInvalidatedEvent;
import asw.efood.consumerservice.event.OrderConsumerValidatedEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/* Test unitari per un servizio del dominio,
 * basato su mock dei servizi infrastrutturali utilizzati (repository e event publisher). */
@SpringBootTest
public class ConsumerServiceTests {

	/* vedi anche https://dzone.com/articles/use-mockito-mock-autowired */

	/* il servizio da testare, in cui iniettare i successivi mock */
	@InjectMocks
	private ConsumerService consumerService;
	/* le dipendenze del servizio da testare */
	@Mock
	private ConsumerRepository consumerRepository;
	@Mock
	private DomainEventPublisher domainEventPublisher;

	private static final Long CONSUMER_ID = 42L;
	private static final String CONSUMER_FIRST_NAME = "Mario";
	private static final String CONSUMER_LAST_NAME = "Rossi";

	private static final Long INVALID_CONSUMER_ID = 81L;

	private static final Long VALID_ORDER_ID = 142L;
	private static final Long INVALID_ORDER_ID = 181L;


	@Before
	public void setup() {
		/* inizializza i mock ed il servizio da testare */
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createConsumerTest() {
		/* verifica che, quando viene usato il servizio per creare un consumatore:
		 * 1) il consumatore viene salvato tramite il repository e
		 * 2) viene pubblicato un evento di creazione del consumatore */

		/* configura ConsumerRepository.save per settare l'id del consumatore */
		when(consumerRepository.save(any(Consumer.class)))
				.then(invocation -> {
					Consumer consumer = (Consumer) invocation.getArguments()[0];
					consumer.setId(CONSUMER_ID);
					return consumer;
				});

		/* invoca la creazione del consumatore */
		Consumer consumer = consumerService.create(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);

		/* verifica che il consumatore è stato salvato */
		verify(consumerRepository)
				.save(same(consumer));
		/* verifica che è stato creato un evento di creazione del consumatore */
		verify(domainEventPublisher)
				.publish(new ConsumerCreatedEvent(CONSUMER_ID, CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME), ConsumerServiceChannel.consumerServiceChannel);
	}

	@Test
	public void finConsumerdByIdTest() {
		/* verifica che, quando viene usato il servizio per cercare un consumatore:
		 * 1) il consumatore viene cercato tramite il repository */

		/* configura ConsumerRepository.findById per trovare il consumatore */
		when(consumerRepository.findById(CONSUMER_ID))
//				.thenReturn(Optional.of(CONSUMER));
				.then(invocation -> {
					Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
					consumer.setId(CONSUMER_ID);
					return Optional.of(consumer);
				});

		/* invoca la ricerca del consumatore */
		Consumer consumer = consumerService.findById(CONSUMER_ID);

		/* verifica che il consumatore è stato trovato */
		verify(consumerRepository)
				.findById(same(CONSUMER_ID));
		assertThat(consumer.getFirstName()).isEqualTo(CONSUMER_FIRST_NAME);
		assertThat(consumer.getLastName()).isEqualTo(CONSUMER_LAST_NAME);
	}

	@Test
	public void validateOrderConsumerTest() {
		/* verifica che, quando viene richiesta la validazione del consumatore di un ordine,
		 * se questo c'è allora viene pubblicato un evento di validazione del consumatore */

		/* configura ConsumerRepository.findById per trovare il consumatore */
		when(consumerRepository.findById(CONSUMER_ID))
				.then(invocation -> {
					Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
					consumer.setId(CONSUMER_ID);
					return Optional.of(consumer);
				});

		/* invoca la richiesta di validazione di un ordine da parte del consumatore */
		consumerService.validateOrderConsumer(VALID_ORDER_ID, CONSUMER_ID);

		/* verifica che il consumatore è stato cercato */
		verify(consumerRepository).findById(same(CONSUMER_ID));
		/* verifica che è stato creato un evento di validazione del consumatore dell'ordine */
		verify(domainEventPublisher)
				.publish(new OrderConsumerValidatedEvent(VALID_ORDER_ID, CONSUMER_ID), ConsumerServiceChannel.consumerServiceChannel);
	}

	@Test
	public void invalidateOrderConsumerTest() {
		/* verifica che, quando viene richiesta la validazione del consumatore di un ordine,
		 * se questo non c'è allora viene pubblicato un evento di invalidazione del consumatore */

		/* configura ConsumerRepository.findById per non trovare il consumatore */
		when(consumerRepository.findById(INVALID_CONSUMER_ID))
				.then(invocation -> {
					return Optional.empty();
				});

		/* invoca la richiesta di validazione di un ordine da parte del consumatore */
		consumerService.validateOrderConsumer(INVALID_ORDER_ID, INVALID_CONSUMER_ID);

		/* verifica che il consumatore è stato cercato */
		verify(consumerRepository).findById(same(INVALID_CONSUMER_ID));
		/* verifica che è stato creato un evento di invalidazione del consumatore dell'ordine */
		verify(domainEventPublisher)
				.publish(new OrderConsumerInvalidatedEvent(INVALID_ORDER_ID, INVALID_CONSUMER_ID), ConsumerServiceChannel.consumerServiceChannel);
	}


}

