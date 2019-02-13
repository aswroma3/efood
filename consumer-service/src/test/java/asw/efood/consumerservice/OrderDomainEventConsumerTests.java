package asw.efood.consumerservice;

import asw.efood.common.event.DomainEvent;
import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.consumerservice.messaging.OrderDomainEventConsumer;
import asw.efood.consumerservice.web.ConsumerController;
import asw.efood.consumerservice.web.CreateConsumerRequest;
import asw.efood.consumerservice.web.CreateConsumerResponse;
import asw.efood.consumerservice.web.GetConsumerResponse;
import asw.efood.orderservice.OrderServiceChannel;
import asw.efood.orderservice.event.LineItem;
import asw.efood.orderservice.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/* Test unitari per un consumeatore di eventi, basato sui mock dei servizi di dominio utilizzati. */
@SpringBootTest
public class OrderDomainEventConsumerTests {

	/* il servizio da testare, in cui iniettare i successivi mock */
	@InjectMocks
	private OrderDomainEventConsumer orderDomainEventConsumer;
	/* le dipendenze del servizio da testare */
	@Mock
	private ConsumerService consumerService;

	private static final Long ORDER_ID = 142L;
	private static final Long CONSUMER_ID = 42L;
	private static final Long RESTAURANT_ID = 242L;

	@Before
	public void setup() {
		/* inizializza i mock ed il servizio da testare */
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void listenOrderCreatedEventTest() throws Exception {
		/* verifica il comportamento relativo alla ricezione di un evento di tipo OrderCreatedEvent */

		/* non è necessario configurare ConsumerService.validateOrderConsumer per validare il consumatore, perché è void */

		/* invoca l'operazione di ricezione di un evento di tipo OrderCreatedEvent */
		List<LineItem> lineItems = new ArrayList<>();
		lineItems.add(new LineItem("Pizza", 1));
		OrderCreatedEvent event = new OrderCreatedEvent(ORDER_ID, CONSUMER_ID, RESTAURANT_ID, lineItems);
		ConsumerRecord<String, DomainEvent> record = new ConsumerRecord<>(OrderServiceChannel.orderServiceChannel, 0,0, "1", event);
		orderDomainEventConsumer.listen(record);

		/* verifica che il servizio è stato invocato  */
		verify(consumerService).validateOrderConsumer(same(ORDER_ID), same(CONSUMER_ID));
	}

}

