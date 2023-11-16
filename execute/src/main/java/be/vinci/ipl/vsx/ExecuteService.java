package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.repositories.OrderProxy;
import org.springframework.stereotype.Service;

@Service
public class ExecuteService {
    private final OrderProxy orderProxy;

  public ExecuteService(OrderProxy orderProxy) {
    this.orderProxy = orderProxy;
  }

  public void executeTransaction(Transaction transaction) {
    orderProxy.updateOne(transaction.getBuy_order_guid(),transaction.getQuantity());
    orderProxy.updateOne(transaction.getSell_order_guid(),transaction.getQuantity());
  }
}
