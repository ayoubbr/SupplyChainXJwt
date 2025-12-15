package ma.youcode.supplyChainX.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SupplyOrderRequest {

    private LocalDate orderDate;
    private Long supplierId;
    private String status;
    private List<RawMaterialQuantity> rawMaterials;

    @Setter
    @Getter
    public static class RawMaterialQuantity {
        private Long rawMaterialId;
        private int quantity;
    }
}
