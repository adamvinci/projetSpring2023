package be.vinci.ipl.vsx.wallet.wallet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionDTO {

    private String ticker;
    private double quantity;
    private Number unitValue;


}
