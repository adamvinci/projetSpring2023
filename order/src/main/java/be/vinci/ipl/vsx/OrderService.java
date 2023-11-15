package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.Order.OrderSide;
import be.vinci.ipl.vsx.Order.OrderType;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Iterable<Order> readAll() {
    return orderRepository.findAll();
  }

  public boolean createOne(Order order) {
      if (order.getType() == OrderType.LIMIT && order.getLimit() == null) {
          return false;
      }
    orderRepository.save(order);
    return true;
  }

  public Order readOne(String guid) {
    return orderRepository.findById(guid).orElse(null);
  }

  public void changeQuantity(Order order, int filled) {
    if ((order.getFilled() + filled) > order.getQuantity()) {
      return;
    }

    order.setFilled(filled);
    orderRepository.save(order);

  }

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
