package ma.youcode.supplyChainX.model;

import jakarta.persistence.*;
import ma.youcode.supplyChainX.shared.enums.SupplyOrderStatus;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.youcode.supplyChainX.model.SupplyOrderRawMaterial;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply_orders")
public class SupplyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private SupplyOrderStatus status;

    @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "supplyOrder", cascade = CascadeType.ALL)
    private List<SupplyOrderRawMaterial> supplyOrderRawMaterials;

    // Getters & Setters
}

