package ma.youcode.supplyChainX.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply_order_raw_materials")
public class SupplyOrderRawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RawMaterial rawMaterial;

    @ManyToOne
    private SupplyOrder supplyOrder;

    private int quantity;

    // Getters & Setters
}

