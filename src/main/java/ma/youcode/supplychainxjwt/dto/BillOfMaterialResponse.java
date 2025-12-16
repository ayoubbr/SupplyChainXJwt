package ma.youcode.supplychainxjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillOfMaterialResponse {
    private Long billOfMaterialId;
    private String productName;
    private Long productStock;
    private String rawMaterialName;
    private Long rawMaterialStock;
    private Integer quantityPerProduct;
}
