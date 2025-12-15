package ma.youcode.supplyChainX.model;

import jakarta.persistence.*;
import ma.youcode.supplyChainX.model.BillOfMaterial;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "raw_materials")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int stock;
    private int stockMin;
    private String unit;

    @ManyToMany
    @JoinTable(
            name = "rawmaterial_supplier",
            joinColumns = @JoinColumn(name = "rawmaterial_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "rawMaterial")
    private List<BillOfMaterial> billOfMaterials;

    @OneToMany(mappedBy = "rawMaterial")
    private List<SupplyOrderRawMaterial> supplyOrderRawMaterials;
}
