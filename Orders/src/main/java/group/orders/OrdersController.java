package group.orders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    private final OrdersService ordersService;

    @PostMapping("/make_order")
    public String makeOrder(@RequestBody Order order) {
        ordersService.sendOrder(order);
        return "Order made successfully";
    }
}
