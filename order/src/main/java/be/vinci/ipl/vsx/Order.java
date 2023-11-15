package be.vinci.ipl.vsx;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String guid;
    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private int timestamp = (int) Instant.now().getEpochSecond();


    @Column(nullable = false,length = 4)
    private String ticker;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderSide side;

  /*
  Can be null so we use Double instead of double
   */
    private Number limite;

    /*
    Not null dans la DB parce que valeur par default est de 0 vu qu'on utilise int
     */
    @Column(nullable = false)
    private int filled;

    public enum OrderType {
        MARKET, LIMIT,
    }

    public enum OrderSide{
        BUY, SELL,
    }

    public boolean invalid() {
        return owner == null || owner.isBlank() ||
            ticker == null || ticker.isBlank() || ticker.length()>4 ||
            quantity <=0 || type == null || side == null ;
    }


}
