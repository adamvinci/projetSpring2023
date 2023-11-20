package be.vinci.ipl.vsx.repositories;

import be.vinci.ipl.vsx.models.Order;
import be.vinci.ipl.vsx.models.Order.OrderSide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to manage orders in the data store.
 */
@Repository
public interface OrderRepository extends CrudRepository<Order,String> {

  /**
   * Retrieve a list of orders by owner.
   * @param owner The owner's username
   * @return list of orders for the specified owner (potentially empty).
   */
  Iterable<Order> findByOwner(String owner);

  /**
   * Retrieve a list of orders by ticker and order side.
   * @param ticker The ticker alphanumeric code
   * @param side The side of the orders to list (buy or sell orders).
   * @return list of orders
   */
  Iterable<Order> findByTickerAndSide(String ticker, OrderSide side);
}
