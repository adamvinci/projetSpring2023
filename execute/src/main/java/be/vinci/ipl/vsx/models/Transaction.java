package be.vinci.ipl.vsx.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A transaction generated on the exchange platform
 */

@Getter
@Setter
@ToString
@NoArgsConstructor

public class Transaction {

  /**
   * Alphanumeric id of a stock
   */
  private String ticker;

  /**
   * Username of the investor who sells
   */
  private String seller;

  /**
   * Username of the investor who buy
   */
  private String buyer;

  /**
   * GUID of the buy order used for this transaction
   */
  private String buy_order_guid;

  /**
   * GUID of the sell order used for this transaction.
   */
  private String sell_order_guid;

  /**
   * Quantity of stocks exchanged.
   */
  private int quantity;
  /**
   * Price at which the transaction was determined.
   */
  private Number price;

  /**
   * Checks whether the current instance represents an invalid financial transaction.
   *
   * @return true if the financial instance is invalid or false if valid
   */
  public boolean invalid() {
    return seller == null || seller.isBlank() || buyer == null || buyer.isBlank() ||
        buy_order_guid == null || buy_order_guid.isBlank() || sell_order_guid == null
        || sell_order_guid.isBlank() ||
        ticker == null || ticker.isBlank() || ticker.length() > 4 ||
        quantity <= 0 || price == null || price.doubleValue() <= 0;
  }
}
