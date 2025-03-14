package group.payment;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.lang.String;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentService {
    private final Logger logger;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int retry = 3;
    private int retryCount = 0;

    public PaymentService(Logger logger, KafkaTemplate<String, String> kafkaTemplate) {
        this.logger = logger;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void handleOrder(String order){
        logger.info("Received order: {}", order);
        try {
            // Логика оплаты заказа...

            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("payed_orders", order);

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
            logger.info("Order payed successfully: {}", order);
        } catch (Exception e) {
            logger.error("Error paying order: {}", order, e);
            throw e;
        }

    }
}
