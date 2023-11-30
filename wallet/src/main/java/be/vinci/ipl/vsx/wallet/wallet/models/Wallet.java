package be.vinci.ipl.vsx.wallet.wallet.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Wallet {

    @Id
    private String username;

    private double netWorth;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Position> positions;



}
