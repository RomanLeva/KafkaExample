package group.shipping;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@EnableKafka
@RestController
public class ShippingController {
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @KafkaListener(topics = "payed_orders", groupId = "shipping-group",  containerFactory = "kafkaListenerContainerFactory")
    public void handlePayedOrder(String order) {
        shippingService.handleOrder(order);

    }
}
