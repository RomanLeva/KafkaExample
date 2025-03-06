package group.orders;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrdersService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger;
    private static final int retry = 3;
    private int retryCount = 0;

    public OrdersService(KafkaTemplate<String, String> kafkaTemplate, Logger logger) {

        this.kafkaTemplate = kafkaTemplate;
        this.logger = logger;
    }

    public void sendOrder(Order order) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("new_orders", order.getDescription());

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                logger.info("Message sent successfully: " +
                        "topic = " + metadata.topic() +
                        ", partition = " + metadata.partition() +
                        ", offset = " + metadata.offset());
            } else {
                if (retryCount >= retry) return;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                logger.info("Failed to send message: " + ex.getMessage() + " Trying one more time..." + " Retries left: " + retryCount);
                retryCount++;
                sendOrder(order);
            }
        });

    }
}
