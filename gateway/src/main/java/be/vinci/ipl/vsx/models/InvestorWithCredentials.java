package be.vinci.ipl.vsx.models;


import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestorWithCredentials {

  private String username;
  private String email;
  private String firstname;
  private String lastname;
  private LocalDate birthdate;
  private String password;
  public Investor toInvestor() {
    return new Investor(username,email,firstname, lastname, birthdate);
  }
  public Credentials toCredentials() {
    return new Credentials(username, password);
  }

}
