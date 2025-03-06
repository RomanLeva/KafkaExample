package group.notifications;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final Logger logger;


    public NotificationService(Logger logger) {
        this.logger = logger;
    }

    public void handleOrder(String order){
        logger.info("Received order: {}", order);
        try {
            // Логика отправки уведомлений заказа...

            logger.info("Order notification sent successfully: {}", order);
        } catch (Exception e) {
            logger.error("Error of notification: {}", order, e);
            throw e;
        }

    }
}
