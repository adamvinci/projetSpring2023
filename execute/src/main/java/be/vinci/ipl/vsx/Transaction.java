package be.vinci.ipl.vsx;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Transaction {

  private String ticker;

  private String seller;

  private String buyer;

  private String buy_order_guid;

  private String sell_order_guid;

  private int quantity;

  private Number price;

  public boolean invalid() {
    return seller == null || seller.isBlank() ||buyer == null || buyer.isBlank() ||
        buy_order_guid == null || buy_order_guid.isBlank() ||sell_order_guid == null || sell_order_guid.isBlank() ||
        ticker == null || ticker.isBlank() || ticker.length()>4 ||
        quantity <=0 || price == null || price.doubleValue()<=0 ;
  }
}
