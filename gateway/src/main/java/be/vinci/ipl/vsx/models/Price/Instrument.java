package be.vinci.ipl.vsx.models.Price;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "instrument")
public class Instrument {
  @Id
  private String ticker;
  private Number price;

}
