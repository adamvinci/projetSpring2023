package be.vinci.ipl.vsx.models.Wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Position {

    /**
     * Unique identifier for the position.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username associated with the position.
     */
    private String username;

    /**
     * Ticker symbol representing the position.
     */
    private String ticker;

    /**
     * Quantity of the asset held in the position.
     */
    private double quantity;



}
