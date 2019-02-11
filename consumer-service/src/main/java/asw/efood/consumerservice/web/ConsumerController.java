package asw.efood.consumerservice.web;

import asw.efood.consumerservice.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/consumers")
public class ConsumerController {

	@Autowired
	private ConsumerService consumerService;

	/*** Per l'uso di CreateConsumerResponse, vedi la discussione dell'operazione GET /consumers/{consumerId} */
//	@RequestMapping(path="/", method=RequestMethod.POST)
//	public Consumer createConsumer(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName) {
//		Consumer consumer = consumerService.create(firstName, lastName);
//		return consumer;
//	}

	/***
	 * L'operazione POST /consumers potrebbe ricevere i parametri mediante @RequestParam.
	 * Però è probabilmente più flessibile ricevere i parametri mediante un oggetto "richiesta"
	 */
//	@RequestMapping(path="/", method=RequestMethod.POST)
//	public CreateConsumerResponse createConsumer(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName) {
//		Consumer consumer = consumerService.create(firstName, lastName);
//		return new CreateConsumerResponse(consumer);
//	}

	/** Crea un nuovo consumatore. */
	@RequestMapping(path="/", method=RequestMethod.POST)
	public CreateConsumerResponse createConsumer(@RequestBody CreateConsumerRequest request) {
		Consumer consumer = consumerService.create(request.getFirstName(), request.getLastName());
		return makeCreateConsumerResponse(consumer);
	}

	private CreateConsumerResponse makeCreateConsumerResponse(Consumer consumer) {
		return new CreateConsumerResponse(consumer.getId());
	}

	/***
	 * L'operazione GET /consumers/{consumerId} potrebbe restituire direttamente uno Consumer (l'entità).
	 * Tuttavia, il JSON che viene generato conterrebbe tutte e sole le proprietà (metodi get pubblici)
	 * e questo potrebbe non essere sempre adeguato
	 * (ad esempio, se alcuni dati devono essere pubblici all'interno del servizio ma non all'esterno).
	 *
	 * Per risolvere questo problema si può usare una classe Presentation Model (Fowler)
	 * associata a questa specifica richiesta, GetConsumerResponse
	 */
//	@RequestMapping(path="/{consumerId}", method=RequestMethod.GET)
//	public Consumer getConsumer(@PathVariable long consumerId) {
//		Consumer consumer = consumerService.findById(consumerId);
//		return consumer;
//	}

	/***
	 * L'operazione GET /consumers/{consumerId} potrebbe restituire GetConsumerResponse.
	 * Tuttavia, se l'utente non viene trovato, a runtime viene generata un'eccezione.
	 *
	 * Per risolvere questo problema l'operazione può restituire una ResponseEntity<GetConsumerResponse>
	 * che può avere uno stato http associato
	 */
//	@RequestMapping(path="/{consumerId}", method=RequestMethod.GET)
//	public GetConsumerResponse getConsumer(@PathVariable long consumerId) {
//		Consumer consumer = consumerService.findById(consumerId);
//		return new GetConsumerResponse(consumer);
//	}

	/***
	 * GetConsumerResponse potrebbe essere parametrico rispetto a Consumer,
	 * ma questo renderebbe GetConsumerResponse dipendente dalle classi di dominio di questo servizio.
	 *
	 * Invece si può avere una classe GetConsumerResponse definita in un package separato (api) autonomo,
	 * che non dipende dalle classi di dominio di questo package.
	 */
//	@RequestMapping(path="/{consumerId}", method=RequestMethod.GET)
//	public ResponseEntity<GetConsumerResponse> getConsumer(@PathVariable long consumerId) {
//		Consumer consumer = consumerService.findById(consumerId);
//		if (consumer!=null) {
//			return new ResponseEntity<>(new GetConsumerResponse(consumer), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}


	/** Trova il consumatore con consumerId. */
	@RequestMapping(path="/{consumerId}", method=RequestMethod.GET)
	public ResponseEntity<GetConsumerResponse> getConsumer(@PathVariable String consumerId) {
		Consumer consumer = consumerService.findById(consumerId);
		if (consumer!=null) {
			return new ResponseEntity<>(makeGetConsumerResponse(consumer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private GetConsumerResponse makeGetConsumerResponse(Consumer consumer) {
		return new GetConsumerResponse(consumer.getId(), consumer.getFirstName(), consumer.getLastName());
	}

	/** Trova tutti i consumatori. */
	@RequestMapping(path="/", method=RequestMethod.GET)
	public ResponseEntity<GetConsumersResponse> getConsumers() {
		List<Consumer> consumers = consumerService.findAll();
		if (consumers!=null) {
			return new ResponseEntity<>(makeGetConsumersResponse(consumers), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private GetConsumersResponse makeGetConsumersResponse(List<Consumer> consumers) {
		List<GetConsumerResponse> responses =
			consumers
				.stream()
				.map(c -> makeGetConsumerResponse(c))
				.collect(Collectors.toList());
		return new GetConsumersResponse(responses);
	}

}

