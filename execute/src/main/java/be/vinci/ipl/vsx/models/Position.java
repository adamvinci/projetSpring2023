package be.vinci.ipl.vsx.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
