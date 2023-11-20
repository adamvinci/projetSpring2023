package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Order;
import be.vinci.ipl.vsx.models.Order.OrderSide;
import be.vinci.ipl.vsx.models.Order.OrderType;
import be.vinci.ipl.vsx.repositories.OrderRepository;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

/**
 * Service class for managing orders.
 */
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * Create a new order and saves it to the data store.
   * @param order to create
   * @return true if the order is created, false if the order type is limit and there is no limit price
   */
  public boolean createOne(Order order) {
      if (order.getType() == OrderType.LIMIT && order.getLimit() == null) {
          return false;
      }
    orderRepository.save(order);
    return true;
  }

  /**
   * Reads a order in repository
   * @param guid Guid of the order being reviewed
   * @return The order, or null if the order couldn't be found
   */
  public Order readOne(String guid) {
    return orderRepository.findById(guid).orElse(null);
  }

  /**
   * Changes the filled quantity of an existing order and save it .
   * @param order  The order to be updated.
   * @param filled The new quantity to be filled for the order
   */
  public void changeQuantity(Order order, int filled) {
    if ((order.getFilled() + filled) > order.getQuantity()) {
      return;
    }

    order.setFilled(filled);
    orderRepository.save(order);

  }

  /**
   * Reads all orders from a user
   * @param username Username of the user
   * @return The list of order from this user (potentially empty)
   */
  public Iterable<Order> readByUser(String username) {
    return orderRepository.findByOwner(username);
  }
  public Iterable<Order> readByTickerAndSide(String ticker, OrderSide side){
    Iterable<Order> allOrders = orderRepository.findByTickerAndSide(ticker, side);

    return StreamSupport.stream(allOrders.spliterator(), false)
        .filter(order -> order.getQuantity() > order.getFilled())
        .collect(Collectors.toList());
  }
}
