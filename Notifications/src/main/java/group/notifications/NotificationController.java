package group.notifications;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@EnableKafka
@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "payed_orders", groupId = "sent-group",  containerFactory = "kafkaListenerContainerFactory")
    public void handleSippingOrder(String order) {
        notificationService.handleOrder(order);

    }
}
