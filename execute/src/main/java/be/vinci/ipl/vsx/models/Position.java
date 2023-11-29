package be.vinci.ipl.vsx.models;

import lombok.Getter;

/**
 * Represents a financial position, which can be either a stock position or a cash position.
 */
@Getter
public class Position {
  private String ticker;
  private int quantity;
  private Number unitValue;

  public Position(String ticker, int quantity, Number unitValue) {
    this.ticker = ticker;
    this.quantity = quantity;
    this.unitValue = unitValue;
  }

}
