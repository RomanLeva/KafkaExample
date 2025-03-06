package group.payment;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@EnableKafka
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "new_orders", groupId = "payment-group",  containerFactory = "kafkaListenerContainerFactory")
    public void handleNewOrder(String order) {
        paymentService.handleOrder(order);

    }
}
