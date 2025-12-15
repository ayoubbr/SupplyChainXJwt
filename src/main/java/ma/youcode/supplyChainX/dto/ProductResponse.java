package ma.youcode.supplyChainX.dto;


import lombok.Data;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private double cost;
    private int productionTime;
    private int stock;

    private List<BillOfMaterialResponseDTO> billOfMaterials;

    @Data
    public static class BillOfMaterialResponseDTO {
        private Long id;
        private Long rawMaterialId;
        private String rawMaterialName;
        private int quantity;
    }
}

