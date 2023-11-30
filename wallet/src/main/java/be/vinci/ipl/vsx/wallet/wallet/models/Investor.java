package be.vinci.ipl.vsx.wallet.wallet.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "investor")
public class Investor {

    /**
     * Unique identifier for the investor.
     */
    @Id
    private String username;

    /**
     * Email address of the investor.
     */
    private String email;

    /**
     * First name of the investor.
     */
    private String firstname;

    /**
     * Last name of the investor.
     */
    private String lastname;

    /**
     * Date of birth of the investor.
     */
    private LocalDate birthdate;


}
