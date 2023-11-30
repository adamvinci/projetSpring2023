package be.vinci.ipl.vsx.data;

import be.vinci.ipl.vsx.models.*;
import be.vinci.ipl.vsx.models.Order.FilledQuantity;
import be.vinci.ipl.vsx.models.Order.Order;
import be.vinci.ipl.vsx.models.Order.OrderSide;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "order")
public interface OrderProxy {

  @PostMapping("/order")
  Order createOrder(@RequestBody Order order);

  @GetMapping("/order/{guid}")
  Order readOrder(@PathVariable String guid);

  @PatchMapping("/order/{guid}")
  void updateOrder(@PathVariable String guid, @RequestBody FilledQuantity filled);

  @GetMapping("/order/by-user/{username}")
  Iterable<Order> readAllOrdersByUser(@PathVariable String username);

  @GetMapping("/order/open/by-ticker/{ticker}/{side}")
  Iterable<Order> getOrderByTickerAndBySide(@PathVariable String ticker, @PathVariable OrderSide side);
}
