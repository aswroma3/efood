package asw.efood.orderservice.adapter.consumerservice.grpc;

import asw.efood.consumerservice.grpc.*;

import asw.efood.orderservice.domain.ConsumerServiceAdapter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.ListenableFuture;

@Service
@Primary
public class ConsumerServiceGrpcAdapterImpl implements ConsumerServiceAdapter {

    private Logger logger = Logger.getLogger("ConsumerServiceAdapter");

    private final ManagedChannel channel;
    private final ConsumerServiceGrpc.ConsumerServiceBlockingStub blockingStub;
    private final ConsumerServiceGrpc.ConsumerServiceFutureStub futureStub;

    private static final String HOST = "consumer-service";
    private static final int PORT = 50051;

    public ConsumerServiceGrpcAdapterImpl() {
        this(HOST, PORT);
    }

    public ConsumerServiceGrpcAdapterImpl(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    public ConsumerServiceGrpcAdapterImpl(ManagedChannel channel) {
        this.channel = channel;
        this.blockingStub = ConsumerServiceGrpc.newBlockingStub(channel);
        this.futureStub = ConsumerServiceGrpc.newFutureStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /* usa il future stub */
    public boolean validateConsumer(Long consumerId) {
        logger.info("Looking for consumer with " + consumerId);
        GetConsumerRequest request = GetConsumerRequest.newBuilder().setConsumerId(consumerId).build();
        GetConsumerReply reply = null;
        try {
            ListenableFuture<GetConsumerReply> futureReply = futureStub.getConsumer(request);
            reply = futureReply.get();
            if (reply != null) {
                logger.info("Consumer found: " + reply.getFirstName() + " " + reply.getLastName());
            } else {
                logger.info("Consumer not found");
            }
        } catch (StatusRuntimeException e) {
            logger.info("RPC failed: " + e.getStatus());
        } catch (InterruptedException e) {
            logger.info("InterruptedException: " + e.toString());
        } catch (ExecutionException e) {
            logger.info("ExecutionException: " + e.toString());
        }
        return reply!=null;
    }

    /* usa il blocking stub */
    public boolean validateConsumerBlocking(Long consumerId) {
        logger.info("Looking for consumer with " + consumerId);
        GetConsumerRequest request = GetConsumerRequest.newBuilder().setConsumerId(consumerId).build();
        GetConsumerReply reply = null;
        try {
            reply = blockingStub.getConsumer(request);
            if (reply != null) {
                logger.info("Consumer found: " + reply.getFirstName() + " " + reply.getLastName());
            } else {
                logger.info("Consumer not found");
            }

        } catch (StatusRuntimeException e) {
            logger.info("RPC failed: " + e.getStatus());
        }
        return reply!=null;
    }
}
