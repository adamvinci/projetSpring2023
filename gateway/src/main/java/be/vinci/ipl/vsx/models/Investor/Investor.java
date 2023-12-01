package be.vinci.ipl.vsx.models.Investor;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investor {

  private String username;
  private String email;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
}
