package group.payment;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.String;

@Service
public class PaymentService {
    private final Logger logger;

    public PaymentService(Logger logger) {
        this.logger = logger;
    }

    public void handleOrder(String order){
        logger.info("Received order: {}", order);
        try {
            // Логика обработки заказа
            logger.info("Order processed successfully: {}", order);
        } catch (Exception e) {
            logger.error("Error processing order: {}", order, e);
            throw e;
        }

    }
}
