package be.vinci.ipl.vsx.wallet.wallet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionDTO {


    /**
     * Ticker symbol representing the position.
     */
    private String ticker;

    /**
     * Quantity of the asset held in the position.
     */
    private double quantity;

    
    /**
     * Unit value of the asset.
     */
    private Number unitValue;


}
