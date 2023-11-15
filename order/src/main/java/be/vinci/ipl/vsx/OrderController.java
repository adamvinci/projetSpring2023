package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.Order.OrderSide;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        if(order.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean order1 = orderService.createOne(order);
        if(!order1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(order,HttpStatus.CREATED);

    }
    @GetMapping("/order")
    public Iterable<Order> readAll(){
        return orderService.readAll();
    }

    @GetMapping("/order/{guid}")
    public ResponseEntity<Order> readOne(@PathVariable String guid) {
        Order order = orderService.readOne(guid);
        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/order/{guid}")
    public ResponseEntity<Void> updateOne(@PathVariable String guid,@RequestBody Integer filled){
        Order order = orderService.readOne(guid);
        if(order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        orderService.changeQuantity(order,filled);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/order/by-user/{username}")
    public ResponseEntity<Iterable<Order> >getOrderByUser(@PathVariable String username){
        Iterable<Order> orderByUsername= orderService.readByUser(username);
        if(!orderByUsername.iterator().hasNext()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(orderByUsername,HttpStatus.OK);
    }
    @GetMapping("/order/open/by-ticker/{ticker}/{side}")
    public Iterable<Order> getOrderByTickerAndBySide(@PathVariable String ticker,@PathVariable OrderSide side){
        return orderService.readByTickerAndSide(ticker,side);
    }
}
