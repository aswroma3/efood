package asw.efood.consumerservice;

import asw.efood.consumerservice.domain.Consumer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/* Test unitari per un'entità del dominio. */
public class ConsumerUnitTests {

	/* per ora c'è molto poco da testare */

	private Consumer consumer;

	@Before
	public void setup() {
		consumer = new Consumer("Mario", "Rossi");
	}

	@Test
	public void testGetName() {
		assertThat(consumer.getFirstName()).isEqualTo("Mario");
		assertThat(consumer.getLastName()).isEqualTo("Rossi");
	}

}

