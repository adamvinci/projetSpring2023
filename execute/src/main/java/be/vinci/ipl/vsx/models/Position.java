package be.vinci.ipl.vsx.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a financial position, which can be either a stock position or a cash position.
 */
@Getter
@Setter
public class Position {
  private String ticker;
  private Number quantity;
  public Position(String ticker, Number quantity) {
    this.ticker = ticker;
    this.quantity = quantity;
  }

}
