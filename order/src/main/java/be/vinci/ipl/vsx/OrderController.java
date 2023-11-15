package be.vinci.ipl.vsx;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOne(@RequestBody Order order) {

    }

    @GetMapping("/order/{guid}")
    public ResponseEntity<Order> readOne(@PathVariable String guid) {

    }
    @PutMapping("/order/{guid}")
    public ResponseEntity<Void> updateOne(@RequestBody int quantite){

    }
}
