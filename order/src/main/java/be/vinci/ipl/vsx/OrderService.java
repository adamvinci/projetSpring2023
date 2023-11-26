package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Order;
import be.vinci.ipl.vsx.models.Order.OrderSide;
import be.vinci.ipl.vsx.models.Order.OrderType;
import be.vinci.ipl.vsx.repositories.MatchingProxy;
import be.vinci.ipl.vsx.repositories.OrderRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing orders.
 */
@Service
public class OrderService {

  private final OrderRepository orderRepository;
//  private final MatchingProxy matchingProxy;

  public OrderService(OrderRepository orderRepository ) {
    this.orderRepository = orderRepository;
  }

  /**
   * Create a new order saves it to the data store and send it to matching microservice.
   * @param order to create
   * @return true if the order is created, false if the order type is limit and there is no limit
   * price
   */
  public boolean createOne(Order order) {
    if (order.getType() == OrderType.LIMIT && order.getLimit() == null) {
      return false;
    }
    orderRepository.save(order);
   // matchingProxy.triggerMatching(order.getTicker(), order);
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

  /**
   * Reads all open orders corresponding to the side and ticker
   * @param ticker The name of the order to read.
   * @param side The subset of open orders to list (buy or sell orders)
   * @return The list of asked open order
   */
  public Iterable<Order> readByTickerAndSide(String ticker, OrderSide side) {
    return orderRepository.findByTickerAndSide(ticker, side);
  }
}
