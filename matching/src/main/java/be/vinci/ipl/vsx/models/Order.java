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
public class Order {


  private String guid;

  private String owner;

  private int timestamp = (int) Instant.now().getEpochSecond();

  private String ticker;

  private int quantity;

  private OrderType type;

  private OrderSide side;


  private Number limit;


  /**
   * Number of stocks already exchanged
   */
  private int filled;

  public enum OrderType {
    MARKET, LIMIT,
  }

  public enum OrderSide {
    BUY, SELL,
  }

}