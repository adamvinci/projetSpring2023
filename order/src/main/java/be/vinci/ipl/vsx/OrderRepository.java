package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.Order.OrderSide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,String> {
  Iterable<Order> findByOwner(String owner);

  Iterable<Order> findByTickerAndSide(String ticker, OrderSide side);
}
