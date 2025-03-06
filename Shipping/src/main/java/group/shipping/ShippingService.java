package group.shipping;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ShippingService {
    private final Logger logger;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int retry = 3;
    private int retryCount = 0;

    public ShippingService(Logger logger, KafkaTemplate<String, String> kafkaTemplate) {
        this.logger = logger;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void handleOrder(String order){
        logger.info("Received order: {}", order);
        try {
            // Логика отправки заказа...

            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("sent_orders", order);

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
                    handleOrder(order);
                }
            });
            logger.info("Payed order goes shipping successfully: {}", order);
        } catch (Exception e) {
            logger.error("Error processing payed order shipment: {}", order, e);
            throw e;
        }

    }
}
