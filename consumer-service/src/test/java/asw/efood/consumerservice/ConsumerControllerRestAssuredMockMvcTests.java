package asw.efood.consumerservice;

import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerService;
import asw.efood.consumerservice.web.ConsumerController;
import asw.efood.consumerservice.web.CreateConsumerRequest;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.RestAssured.*;
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/* Test unitari per un controller, basato su Rest Assured Mock MVC. */
@SpringBootTest
// @RunWith(SpringRunner.class)
public class ConsumerControllerRestAssuredMockMvcTests {

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

	// private MockMvc mockMvc;

	@Before
	public void setup() {
		/* inizializza i mock ed il servizio da testare */
		MockitoAnnotations.initMocks(this);
		// this.mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();
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
//		String jsonRequest =
//				"{ " +
//					"\"firstName\": \"" + CONSUMER_FIRST_NAME + "\", " +
//					"\"lastName\":\"" + CONSUMER_LAST_NAME + "\" " +
//				" }";
		CreateConsumerRequest request = new CreateConsumerRequest();
		request.setFirstName(CONSUMER_FIRST_NAME);
		request.setLastName(CONSUMER_LAST_NAME);
		given().
				standaloneSetup(consumerController).
				body(request).
				contentType("application/json").
		when().
				post("/consumers/").
		then().
				statusCode(200).
				body("consumerId",  equalTo(CONSUMER_ID.intValue()));
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
		given().
				standaloneSetup(consumerController).
		when().
				get("/consumers/{consumerId}", CONSUMER_ID).
		then().
				statusCode(200).
				body("consumerId",  equalTo(CONSUMER_ID.intValue())).
				body("firstName",  equalTo(CONSUMER_FIRST_NAME)).
				body("lastName",  equalTo(CONSUMER_LAST_NAME));

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
		given().
				standaloneSetup(consumerController).
				when().
				get("/consumers/{consumerId}", NONEXISTING_CONSUMER_ID).
				then().
				statusCode(404);
	}

}

