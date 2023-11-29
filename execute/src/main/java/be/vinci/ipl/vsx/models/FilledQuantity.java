package be.vinci.ipl.vsx.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the filled quantity of an order.
 */
@Getter
@Setter
public class FilledQuantity {
  private int filled;
  public FilledQuantity(int quantity) {
    this.filled = quantity;
  }
}
