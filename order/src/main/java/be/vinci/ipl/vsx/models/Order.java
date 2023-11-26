package be.vinci.ipl.vsx.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;

/**
 * An Order passed on the exchange platform
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false)
  private String guid;
  /**
   * Username of the investor
   */
  @Column(nullable = false)
  private String owner;

  /**
   * Time when the order was created since 01/01/1970
   */
  @Column(nullable = false)
  private int timestamp = (int) Instant.now().getEpochSecond();

  /**
   * Alphanumeric id of a stock
   */
  @Column(nullable = false, length = 4)
  private String ticker;

  /**
   * Quantity of stocks to exchange
   */
  @Column(nullable = false)
  private int quantity;

  /**
   * Order type('MARKET','LIMIT')
   */
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderType type;
  /**
   * Order side('MARKET','LIMIT')
   */
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderSide side;


  /**
   * Minimal price to buy/sell a stock
   */
  @Column(name = "quantity_limit")
  private Number limit;


  /**
   * Number of stocks already exchanged
   */
  @Column(nullable = false)
  private int filled;

  public enum OrderType {
    MARKET, LIMIT,
  }

  public enum OrderSide {
    BUY, SELL,
  }

  /**
   * Checks whether the current instance represents an invalid order.
   *
   * @return true if the order instance is invalid or false if valid
   */
  public boolean invalid() {
    return owner == null || owner.isBlank() ||
        ticker == null || ticker.isBlank() || ticker.length() > 4 ||
        quantity <= 0 || type == null || side == null;
  }


}
