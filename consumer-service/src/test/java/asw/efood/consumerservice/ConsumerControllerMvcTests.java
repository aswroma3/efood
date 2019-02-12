package asw.efood.consumerservice;

import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.consumerservice.web.ConsumerController;
import asw.efood.consumerservice.web.CreateConsumerRequest;
import asw.efood.consumerservice.web.CreateConsumerResponse;
import asw.efood.consumerservice.web.GetConsumerResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/* Test unitari per un controller, basato su Spring Mock MVC. */
@SpringBootTest
// @RunWith(SpringRunner.class)
public class ConsumerControllerMvcTests {

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

	private MockMvc mockMvc;

	@Before
	public void setup() {
		/* inizializza i mock ed il servizio da testare */
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();
		// MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// this.mockMvc = MockMvcBuilders.standaloneSetup(consumerController).setMessageConverters(converter).build();
	}

	/* questi test sono in qualche modo complementari a quelli svolti dall'altra classe di test sul controller,
	 * ma operano su richieste e risposte codificate in json
	 * anziché su richieste e risposte rappresentate da oggetti Java */

	@Test
	public void postConsumerTest() throws Exception {
		/* verifica dell'operazione POST /consumers */

		/* configura ConsumerService.create per creare il consumatore */
		when(consumerService.create(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME))
				.then(invocation -> {
					Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
					consumer.setId(CONSUMER_ID);
					return consumer;
				});

		/* invoca l'operazione POST /consumers */
		String jsonRequest =
				"{ " +
					"\"firstName\": \"" + CONSUMER_FIRST_NAME + "\", " +
					"\"lastName\":\"" + CONSUMER_LAST_NAME + "\" " +
				" }";
		mockMvc
				.perform(MockMvcRequestBuilders.post("/consumers/").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(MockMvcResultMatchers.status().isOk())
				/* verifica gli elementi della risposta JSON */
				.andExpect(MockMvcResultMatchers.jsonPath("$.consumerId").value(CONSUMER_ID));
	}


	@Test
	public void getConsumerTest() throws Exception {
		/* verifica dell'operazione GET /consumers/{consumerId} */

		/* configura ConsumerService.findById per trovare il consumatore */
		when(consumerService.findById(CONSUMER_ID))
				.then(invocation -> {
					Consumer consumer = new Consumer(CONSUMER_FIRST_NAME, CONSUMER_LAST_NAME);
					consumer.setId(CONSUMER_ID);
					return consumer;
				});

		/* invoca l'operazione GET /consumers/{consumerId} */
		mockMvc
				.perform(MockMvcRequestBuilders.get("/consumers/{consumerId}", CONSUMER_ID).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				/* verifica gli elementi della risposta JSON */
				.andExpect(MockMvcResultMatchers.jsonPath("$.consumerId").value(CONSUMER_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(CONSUMER_FIRST_NAME))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(CONSUMER_LAST_NAME));
	}

	@Test
	public void getConsumerNotFoundTest() throws Exception {
		/* verifica dell'operazione GET /consumers/{consumerId} */

		/* configura ConsumerService.findById per trovare il consumatore */
		when(consumerService.findById(NONEXISTING_CONSUMER_ID))
				.then(invocation -> {
					return null;
				});

		/* invoca l'operazione GET /consumers/{consumerId} */
		mockMvc
				.perform(MockMvcRequestBuilders.get("/consumers/{consumerId}", NONEXISTING_CONSUMER_ID).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}

