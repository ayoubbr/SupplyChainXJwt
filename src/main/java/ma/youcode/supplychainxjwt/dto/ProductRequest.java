package ma.youcode.supplychainxjwt.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductRequest{
    private String name;
    private double cost;
    private int productionTime;
    private int stock;

    private List<BillOfMaterialDTO> billOfMaterials;

    @Data
    public static class BillOfMaterialDTO {
        private Long rawMaterialId;
        private int quantity;
    }
}
