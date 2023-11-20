package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Transaction;
import be.vinci.ipl.vsx.repositories.OrderProxy;
import org.springframework.stereotype.Service;

/**
 * Service class for managing transaction.
 */
@Service
public class ExecuteService {
    private final OrderProxy orderProxy;

  public ExecuteService(OrderProxy orderProxy) {
    this.orderProxy = orderProxy;
  }

  /**
   * Contact the order service to update the order status
   * Contact the price service to update the stock price
   * Contact the wallet service to update the quantity of stock/cash possessed by the buyer and seller
   * @param transaction
   */
  public void executeTransaction(Transaction transaction) {
    orderProxy.updateOne(transaction.getBuy_order_guid(),transaction.getQuantity());
    orderProxy.updateOne(transaction.getSell_order_guid(),transaction.getQuantity());
  }
}
