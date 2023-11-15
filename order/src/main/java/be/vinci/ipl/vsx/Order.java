package be.vinci.ipl.vsx;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "order")
public class Order {
    @Id
    @Column(nullable = true)
    private String guid;

    @Column(nullable = false)
    private String owner;




}
