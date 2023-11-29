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
@Entity(name="investor")
public class Investor {

    @Id
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;


}
