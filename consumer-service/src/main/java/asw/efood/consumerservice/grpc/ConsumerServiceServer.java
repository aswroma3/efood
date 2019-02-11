package asw.efood.consumerservice.grpc;

import asw.efood.consumerservice.domain.Consumer;
import asw.efood.consumerservice.domain.ConsumerService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

public class ConsumerServiceServer {

    private static final Logger logger = Logger.getLogger("ConsumerServiceServer");

    private int port = 50051;
    private Server server;
    private ConsumerService consumerService;

    public ConsumerServiceServer(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostConstruct
    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new ConsumerServiceImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            logger.info("*** shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            logger.info("*** server shut down");
        }
    }


    private class ConsumerServiceImpl extends ConsumerServiceGrpc.ConsumerServiceImplBase {

        @Override
        public void getConsumer(GetConsumerRequest req, StreamObserver<GetConsumerReply> responseObserver) {
            Consumer consumer = consumerService.findById(req.getConsumerId());
            if (consumer!=null) {
                GetConsumerReply reply = GetConsumerReply.newBuilder()
                        .setConsumerId(consumer.getId())
                        .setFirstName(consumer.getFirstName())
                        .setLastName(consumer.getLastName())
                        .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            } else {
                /* consumer not found */
                /* ma è lecito in questo caso restituire Status.NOT_FOUND? */
                responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND));
                responseObserver.onCompleted();
            }
        }

    }

}
