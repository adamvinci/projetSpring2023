package be.vinci.ipl.vsx.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "credentials")

public class SafeCredentials {

  @Id
  @Column(nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String hashedPassword;
}
