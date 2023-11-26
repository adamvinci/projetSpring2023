package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.FilledQuantity;
import be.vinci.ipl.vsx.models.Order;
import be.vinci.ipl.vsx.models.Order.OrderSide;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling order-related HTTP requests.
 */
@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Endpoint to place an order on the platform
   * @param order the order to be placed
   * @return BAD_REQUEST if the order is invalid or CREATED if the order is successfully placed
   */
  @PostMapping("/order")
  public ResponseEntity<Order> createOne(@RequestBody Order order) {
      if (order.invalid()) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    boolean order1 = orderService.createOne(order);
      if (!order1) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {
          return new ResponseEntity<>(order, HttpStatus.CREATED);
      }
  }

  /**
   * Endpoint to retrieve details of a specific order by its GUID.
   * @param guid The GUID of the specific order to retrieve details.
   * @return NOT_FOUND if the guid does not exist or OK if the order is successfully retrieved
   */
  @GetMapping("/order/{guid}")
  public ResponseEntity<Order> readOne(@PathVariable String guid) {
    Order order = orderService.readOne(guid);
      if (order == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
          return new ResponseEntity<>(order, HttpStatus.OK);
      }
  }

  /**
   * Endpoint to update the filled quantity of an order based on its GUID.
   * @param guid   The GUID of the order to update.
   * @param filled The  quantity of shares that have already been traded for this order.
   * @return NOT_FOUND if the guid does not exist or OK if the order is successfully updated
   */
  @PutMapping("/order/{guid}")
  public ResponseEntity<Void> updateOne(@PathVariable String guid, @RequestBody FilledQuantity filled) {
    Order order = orderService.readOne(guid);
      if (order == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    orderService.changeQuantity(order, filled.getFilled());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Endpoint to retrieve all orders placed by a specific investor.
   * @param username The username of the investor.
   * @return OK with a potentially empty list
   */
  @GetMapping("/order/by-user/{username}")
  public ResponseEntity<Iterable<Order>> getOrderByUser(@PathVariable String username) {
    Iterable<Order> orderByUsername = orderService.readByUser(username);
    return new ResponseEntity<>(orderByUsername, HttpStatus.OK);
  }

  /**
   * Endpoint to retrieve all open orders for a specific ticker and order side.
   * @param ticker The ticker alphanumeric code
   * @param side   The side of the orders to list (buy or sell orders).
   * @return list of orders
   */
  @GetMapping("/order/open/by-ticker/{ticker}/{side}")
  public Iterable<Order> getOrderByTickerAndBySide(@PathVariable String ticker,
      @PathVariable OrderSide side) {
    return orderService.readByTickerAndSide(ticker, side);
  }
}
