package asw.efood.consumerservice;

import asw.efood.common.event.DomainEventPublisher;
import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerRepository;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.consumerservice.event.ConsumerCreatedEvent;
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

/* Test unitari per un servizio del dominio. */
@SpringBootTest
public class ConsumerServiceUnitTests {

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
	private static final Consumer CONSUMER = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);

	@Before
	public void setup() {
		/* inizializza i mocke ed il servizio da testare */
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
	public void findConsumerTest() {
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


}

