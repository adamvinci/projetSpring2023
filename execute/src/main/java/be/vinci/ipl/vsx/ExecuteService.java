package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Transaction;
import be.vinci.ipl.vsx.repositories.OrderProxy;
import be.vinci.ipl.vsx.repositories.PriceProxy;
import org.springframework.stereotype.Service;

/**
 * Service class for managing transaction.
 */
@Service
public class ExecuteService {
    private final OrderProxy orderProxy;
    private final PriceProxy priceProxy;

  public ExecuteService(OrderProxy orderProxy, PriceProxy priceProxy) {
    this.orderProxy = orderProxy;
    this.priceProxy = priceProxy;
  }

  /**
   * Contact the order service to update the order status
   * Contact the price service to update the stock price
   * Contact the wallet service to update the quantity of stock/cash possessed by the buyer and seller
   * @param transaction the transaction which has been executed
   */
  public void executeTransaction(Transaction transaction) {
    orderProxy.updateOne(transaction.getBuy_order_guid(),transaction.getQuantity());
    orderProxy.updateOne(transaction.getSell_order_guid(),transaction.getQuantity());
    priceProxy.updatePrice(transaction.getTicker(),transaction.getPrice());
  }
}
