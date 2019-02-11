# Progetto EFOOD


## Sommario 

Questo diario descrive il percorso evolutivo di definizione del sistema *EFOOD*.
In particolare, descrive le diverse scelte implementative che sono state fatte o sperimentate e come queste poi siano state modificate a seguito di una comprensione migliore (o diversa) delle linee guida che sono state seguite.


## Implementazione iniziale del servizio consumer-service 

L'implementazione è iniziata con il servizio consumer-service. 

Si è scelto di utilizzare (per quanto possibile) DDD e l'architettura esagonale.
Il cuore di ogni esagono è lo strato di **dominio**, nel package *domain*, che deve essere autocontenuto (non deve dipendere da altri strati o package), per favorirne la verifica e l'evoluzione. 

La tecnologia scelta è Spring Boot. 

Il package *domain* contiene le entità del servizio, gli aggregati (per ora non ci sono), i servizi ed i repository.  

Per la persistenza (di per sé è un adapter) si è scelto di usare Spring Data JPA (con un minimo di accoppiamento del dominio dalla tecnologia), annotando le entità con @Entity ed usando un Repository JPA (nel dominio c'è solo l'interfaccia, l'implementazione è automatica). 

### Entità e loro identificatori 

Per identificare le entità e per generare gli identificatori ci sono diverse possibilità (vedi IDDD Capitolo 5, da 173 in poi): 
* Usare *Long* e la generazione di itemId di JPA.
  Soluzione semplice, ma l'itemId non viene assegnato all'entità fino a quando non viene salvata nel db. 
  Pertanto non è possibile creare eventi che contengono l'itemId di un'entità fino a quando questa non viene salvata. 
* Usare *String* generate mediante *UUID*. 
  Risolve i problemi precedenti. 
* Usare una classe value object che incapsula l'itemId. Per esempio, *OrderId*. 
  La soluzione più complessa. 
  
Per semplicità, mi attesto sulla seconda soluzione. 

### Problemi aperti 

Consideriamo la classe *OrderService* del servizio *order-service*. 
Si tratta di un *service* (nel senso di DDD) oppure di un *Application Service*?
Vedi IDDD pagina 267 per la differenza: un Application Service è un facade controller sottile, che non implementa logica applicativa, mentre un service può implementare logica applicativa.
Altro criterio: un Application Service appartiene allo strato Application, e non allo strato Domain. Dunque gli oggetti di dominio non dovrebbero dipendere dagli Application Service.  

Questo distinzione tra Service e Application Service è rilevante. Per esempio, la gestione della creazione di un ordine va fatta in *OrderService* oppure in *Order*? 


## Implementazione di un'API REST per consumer-service 

E' stato poi implementato un **adattatore REST** per il servizio, nel package *web* (ma forse diventerà *rest*).

Il cuore dell'adattatore è un *controller REST*, con le operazioni di interesse. 
Inizialmente solo due: 

* *POST /consumers* per creare un nuovo consumatore
* *GET /consumers/{consumerId}* per ritrovare un consumatore 

### Operazione POST /consumers 

Supponiamo che per creare un consumatore vadano specificati il nome e il cognome. 
Sono state usate diverse soluzioni: 
* passare nome e cognome come due parametri distinti dell'operazione, ricevuti dall'operazione mediante *@RequestParam* 
* usare un oggetto **richiesta** *CreateConsumerRequest*, che incapsula tutti i dati necessari per creare un nuovo consumatore

Inoltre, che risposta deve fornire questa operazione? 
* potrebbe restituire il consumatore appena creato, però non sembra una buona idea restituire direttamente un oggetto di dominio (per esempio, potrebbe avere dati privati) 
* restituire invece un oggetto **risposta** *CreateConsumerResponse*, che contiene solo i dati che si vogliono effettivamente restituire al client della richiesta 

In pratica, con queste ultime scelte si è deciso di definire un *Presentation Model* (o *View Model* per l'adattatore REST, in modo da disaccoppiare il dominio dall'API REST.
Vedi IDDD pagina 516. Ogni Presentation Model contiene i dati del dominio di interesse nel contesto di una specifica operazione o di uno specifico caso d'uso, mentre gli oggetti del dominio sono pensati per supportare tutte le operazioni di tutti i casi d'uso.   

### Operazione GET /consumers/{consumerId}

Oltre alle scelte fatte per l'operazione *POST /consumers*, si è deciso per questa operazione di restituire non *GetConsumerResponse* ma piuttosto *ResponseEntity<GetConsumerResponse>*, in modo da poter associare uno stato HTTP alla risposta. 
Per esempio, restituire *HttpStatus.NOT_FOUND* (senza nessuna *GetConsumerResponse*) nel caso in cui il consumatore non venga trovato. 

Vedi poi anche le considerazioni fatte nel contesto della definizione di un progetto **api** separato per il servizio. 

### Operazioni che restituiscono collezioni  

Come implementare un'operazione che restituisce una collezione? Per esempio, *GET /consumers*? In particolare, che cosa deve restituire una tale operazione? 

Abbiamo deciso di fare come nel progetto FTGO, nel servizio *order-history*, in cui ci sono due operazioni correlate: 
* *GET /orders/{orderId}* che restituisce *GetOrderResponse* 
* *GET /orders* che restituisce *GetOrdersResponse* 
in cui la classe *GetOrdersResponse* contiene una lista di *GetOrderResponse*. 


### Uso di swagger-ui 

Per sperimentare in pratica l'accesso mediante REST al servizio, si è deciso di utilizzare *swagger-ui*, che genera automaticamente un'interfaccia web per accedere alle operazioni fornite dal controller REST. 

## Definizione di un'API REST (separata) 

Facendo un piccolo passo in avanti, bisogna considerare il problema di invocare le operazioni del servizio consumer-service da parte di un altro microservizio, per esempio proprio mediante l'API REST. 
Nel caso in cui anche il microservizio client sia realizzato con Spring Boot (o comunque in Java), anche il servizio client deve specificare classi per le richieste e le risposte. 

In alternativa, le classi per le richieste e per le risposte possono essere messe in un progetto (Gradle) definito dal servizio ed utilizzato da tutti i suoi servizi client. 
Per questo è stato definito anche il progetto *consumer-service-api*, con un package *web* (ma potrebbe diventare *rest*) con tutte le classi richiesta e risposta. 

Oltre alle classi richiesta e risposta, questo package può contenere classi utili per definire i tipi delle richieste e delle risposte. Per esempio, *GetRestaurantMenuResponse* potrebbe richiedere una classe aggiuntiva *MenuLineItem*. 

Nel progetto del servizio principale *consumer-service*, nel package *web* (o *rest*) rimane solo il controller. 

### Dipendenze mutue tra interfaccia e servizio 

Un'ulteriore considerazione. 
Non si vuole né che il dominio di *consumer-service* dipenda da *consumer-service-api* (che ne costituisce l'interfaccia REST), né che il progetto *consumer-service-api* dipenda da *consumer-service* (che ne costituisce l'implementazione). Infatti, poiché un servizio client dipende da *consumer-service-api*, non vogliamo che esso dipenda indirettamente anche da *consumer-service*.

Questo ha richiesto la seguente modifica: 
* Inizialmente si era deciso di far dipendere, per esempio, *GetConsumerResponse* da *Consumer* (del dominio), definendo un costruttore *GetConsumerResponse(Consumer consumer)*. Ma questo viola la richiesta di indipendenza dell'interfaccia dal servizio. 
* Pertanto, *GetConsumerResponse* è stato modificato in modo da non dipendere da *Consumer* (del dominio). 

In pratica, è il controller REST che trasforma gli oggetti di dominio in oggetti richiesta o risposta.

IDDD pagina 517 e successive suggerisce che la trasformazione da oggetti del dominio a richieste/risposte vada fatta da oggetti *Data Transformer* specializzati.  
Noi ci siamo ispirati alla soluzione adottata da FTGO (ad esempio, in *RestaurantController*), in cui la classe controller definisce dei metodi di supporto privati per convertire gli oggetti di dominio in oggetti risposta oppure per estrarre dati di dominio dalle richieste. Ad esempio, *GetRestaurantResponse makeGetRestaurantResponse(Restaurant r)*. Questo consente di tenere compatto il codice per le operazioni di sistema. 
  
### Problemi aperti 

Il package *consumer-service-api* alla fine definisce solo le richieste e le risposte dell'API REST, ma non ne definisce le operazioni. 
Sarebbe utile capire se c'è un modo semplice ed elegante per fornire una definizione precisa dell'API REST di un servizio (o quanto meno per descriverla). 

## Invocazione remota di un servizio  

Consideriamo ora la realizzazione di un client per il servizio *consumer-service*, per esempio da parte del servizio *order-service*. 

Per fare questo è necessario definire un adattatore da *order-service* a *consumer-service*. Inoltre, il dominio di *order-service* non deve dipendere dall'adattatore. 
Soluzione: 

* il dominio di *order-service* contiene un'interfaccia *ConsumerServiceAdapter* per accedere la servizio *consumer-service* 
* c'è un package *adapter.consumerservice* che contiene l'implementazione di questa interfaccia 
* è solo l'implementazione dell'adapter che dipende da *consumer-service-api*

Sono state usate due tecnologie: 
* *RestTemplate* (sincrona, verrà deprecata in Spring Boot) 
* *WebClient* (reattiva)


## GRPC 

E' stata anche considerata la tecnologia *GRPC* per le invocazioni remote. 
L'interfaccia di un servizio viene specificata in un file *proto*, da cui viene generato il codice sia lato server che lato client.

* Lato server, bisogna definire un adattatore, con una classe *Server* che avvia il server GRPC e inoltre specifica come mappare le richieste con i servizi del dominio e poi con le risposte.  
* Lato client, bisogna definire un adattatore che fa le chiamate alle classi proxy generate automaticamente.

Il file *proto* può essere utilmente collocato nel progetto *consumer-service-api*, per evitare una sua ripetizione nei servizi che svolgono il ruolo sia di server che di client. 
Non ho capito perché *Microservices Patterns* non mette il *proto* a fattor comune dentro *consumer-service-api*, ma piuttosto la ripete sia dentro *consumer-service* che dentro il servizi client *order-service*. 


## Gestione di eventi 

Il passaggio successivo è stato di trasformare la logica sincrona di verifica del cliente (*order-service* chiama *consumer-service*) con una logica asincrona, basata su eventi. 

La logica di interesse è questa: quando viene creato un ordine (tra i parametri ha l'itemId di un cliente), bisogna verificare la validità del cliente (nel nostro caso, semplificato, bisogna solo verificare se esiste un cliente con quell'itemId). Inizialmente questa validazione veniva fatta con una invocazione remota sincrona.  

La nuova logica che si vuole implementare è questa: 
* quando *order-service* crea un nuovo ordine, lo mette nello stato PENDING e poi genera un evento di tipo *OrderCreatedEvent* (con itemId dell'ordine, del cliente e del ristorante) 
* quando *consumer-service* riceve un evento di tipo *OrderCreatedEvent*, verifica se il cliente dell'ordine esiste o meno, e genera in corrispondenza un evento di tipo *OrderCustomerValidatedEvent* (oppure *OrderCustomerInvalidatedEvent*) 
* quando *order-service* riceve un evento di tipo *OrderCustomerValidatedEvent* cerca l'ordine eè, se era nello stato PENDING, lo mette nello stato CUSTOMER_VALIDATED 

Ci sarebbe da definire anche la logica di validazione dei dettagli dell'ordine, ma per ora è stata omessa. 

### Il progetto common-events 

Per la gestione degli eventi è stato definito un nuovo progetto *common-events*, che definisce un'infrastruttura minimale per la gestione degli eventi. In particolare, definisce le seguenti interfacce: 
* l'interfaccia marker *DomainEvent*, che deve essere implementata dalle classi per gli eventi 
* l'interfaccia *DomainEventPublisher*, con un'operazione *publish(event, channel)* 
* l'interfaccia *DomainEventListener*, che però per ora non è stata ancora mai utilizzata 

Queste tre interfacce possono essere usate nello strato del dominio dei diversi servizi (sono indipendenti da ogni tecnologia), per esempio dagli oggetti service. 

Inoltre, è stata anche definita una classe *DomainPublisherEventImpl* che implementa l'interfaccia *DomainEventPublisher*, che è un endpoint per Kafka per la pubblicazione di eventi di dominio. 

### Caratteristiche degli eventi

Vedi IDDD capitolo 8. 
* Ogni evento dovrebbe avere un nome che indica un evento già successo nel passato.
* Ogni evento dovrebbe avere un *timestamp* dell'evento. 
* Ogni evento dovrebbe avere gli attributi necessari a ripetere, potenzialmente, l'avvenimento dell'evento, quali gli identificatori degli aggregati coinvolti ed altre proprietà semplici dell'evento avvenuto. 
Da quanto capisco, gli attributi di un evento non devono includere oggetti di dominio (se non itemId di entità ed aggregati).  

Inoltre, gli eventi sono *eventi di dominio*, quindi vanno considerati significativi a livello dell'intero sistema, e non solo all'interno di un singolo servizio. 
Vedi IDDD pagina 302. 

### Generazione di eventi 

Consideriamo il fatto che il servizio *order-service* possa generare eventi di tipo *OrderCreatedEvent*. Che cosa bisogna fare? 

* Nell'*order-service-api* è stato definito un package *event* con tutti gli oggetti evento e i tipi di supporto alla definizione degli eventi. 
* Bisogna notare che DDD prevede che gli eventi siano parte del dominio, e quindi il dominio può dipendere da questi eventi. Questo è in effetti un po' strano, perché gli eventi vengono scambiati tra servizi, e quindi questi eventi sono simili agli oggetti del Presentation Model (package *web* o *rest*) da cui invece il dominio rimane indipendente.
  Tuttavia, IDDD pagina 302 dice che gli eventi sono dei concetti validi nell'intero sistema (e non di un singolo servizio), e questo potrebbe essere il motivo per ammettere questa dipendenza.  
* Dentro il package *event* c'è anche una classe che definisce il nome del canale su cui viaggiano questi eventi. L'ipotesi è che ogni servizio abbia un proprio canale su cui pubblica i propri eventi. 
* Inoltre, quando *OrderService* (del dominio) crea un nuovo ordine, crea anche un evento *OrderCreatedEvent* e lo pubblica sul proprio canale mediante il proprio *DomainEventPublisher*. 

### Problemi aperti 

Leggendo *Implementing DDD* (figura G.6 a pagina xl, figura 8.1 a pagina 287, oppure pagina 297 ed esempio a pagina 300), la generazione degli eventi dovrebbe essere un po' diversa: 

* Dovrebbe essere un aggregato a creare e pubblicare i propri eventi. Dunque, direttamente l'*Order*, e non *OrderService*. 
* Far questo mi sembra problematico, perché quando l'ordine viene creato non ha un proprio identificatore fino a quando l'ordine non viene salvato nella base di dati, e quindi l'ordine non potrebbe creare eventi che contengono il proprio identificatore. 

Bisogna capire come risolvere questi problemi. 

Invece la soluzione del progetto FTGO di *Microservices Patterns* è la seguente: 
* Il Controller REST chiede a *OrderService* di creare l'ordine. 
* *OrderService* chiede a *Order* di eseguire *createOrder*, che crea l'ordine (senza l'itemId) e anche gli eventi associati (sempre senza l'itemId). 
  Tuttavia, *Order::createOrder* non si occupa né di salvare l'ordine né di pubblicare gli eventi. 
* Il controllo torna a *OrderService*, che prima salva l'ordine nella base di dati (gli viene assegnato l'itemId) e poi pubblica gli eventi, passando però come parametro anche l'ordine (che contiene l'itemId). 
  In pratica, insieme ad ogni evento viene trasmesso anche l'itemId dell'ordine a cui si riferisce.
  Per far questo, gli eventi di dominio hanno una ulteriore classificazione intermedia che specifica l'aggregato a cui si riferiescono (per esempio, *OrderDomainEvent*).     

### Ricezione di eventi 

Consideriamo il fatto che il servizio *consumer-service* possa voler ricevere ed elaborare eventi di tipo *OrderCreatedEvent*. Che cosa bisogna fare? 

* Il servizio deve dipendere da *order-service-api* che definisce gli eventi e il canale da cui ricevere gli eventi per gli ordini. 
* Inoltre il servizio deve certamente definire un adattatore per la ricezione di eventi. Nel package *messaging*. 
* In particolare, definisce una classe *OrderDomainEventConsumer* (o *Listener*)  con un metodo annotato *@KafkaListener* in cui è specificato il canale (o i canali) per messaggi di interesse, che viene invocato quando viene ricevuto un messaggio (evento) su quel canale. 
* Nel corpo di questo metodo, c'è una cascata di if-else che serve a capire qual è il tipo di evento ricevuto e, in corrispondenza, ad invocare un'opportuna operazione del dominio (dell'Application Service). 
* Questa operazione può a sua volta generare nuovi eventi di dominio. 

### Problemi aperti 

C'è un modo elegante per evitare la cascata di if-else? 
Serve un meccanismo che, sulla base del tipo dell'evento, invoca un metodo opportuno e gli passa come parametro l'evento.   


## Query 

E' stato poi implementato un servizio *order-history-service* per rispondere a due interrogazioni: 
* *GET /orders/{orderId}* che restituisce informazioni riassuntive sull'ordine 
* *GET /orders/{consumerId}* che restituisce informazioni riassuntive sugli ordini effettuati da un certo consumatore 

L'approccio è ispirato a **CQRS** (anche se CQRS non viene seguito in modo stretto): il servizio riceve gli eventi generati da altri servizi e ne replica localmente le informazioni. 
Poi risponde a queste interrogazioni usando solo dati locali.  

Per ora è stata fatta solo un'implementazione elementare di *GET /orders/{orderId}* (che peraltro fa un bruttissimo join applicativo), però funzionante.  


## Problemi aperti leggendo Implementi DDD 

### Generazione degli identificatori delle entità 

Chi, come e quando vengono generati gli identificatori delle entità e degli aggregati? 
Vedi Capitolo 5, pagine da 173 in poi. 
Una soluzione suggerita è di usare UUID anziché l'itemId generato dal repository quando un aggregato viene salvato. 

Inoltre, per gli identificatori potrebbe essere opportuno usare una classe specifica (per esempio, *CustomerId*), anziché *Long* o *String*, per evitare che un metodo abbia tanti parametri che sono degli identificatori di entità diverse ma che sono tutti di tipo *Long*. 
Per semplicità, per ora usa solo delle stringhe. 

### Creazione di entità 

Dove mettere la logica di creazione di un'entità? 
* Nella classe per l'Application Service? No, dovrebbe essere la più semplice possibile. 
* Nella classe per l'entità, con un metodo statico? Forse è meglio (tranne l'uso di un metodo statico). 


## Altri problemi aperti 

Se spengo un servizio e poi lo riavvio, tutti i suoi dati vengono persi (poiché la base di dati viene ricreata). 
Evitare, se possibile. 
 
Implementare una gestione transazionale degli eventi (se si può fare con Kafka). 