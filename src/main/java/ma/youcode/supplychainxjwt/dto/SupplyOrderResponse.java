package ma.youcode.supplychainxjwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class SupplyOrderResponse {
    private Long id;
    private LocalDate date;
    private String status;
    private SupplierResponse supplier;
    private List<RawMaterialResponse> rawMaterials;

    @Setter
    @Getter
    public static class SupplierResponse {
        private Long id;
        private String name;
        private String contact;
        private Double rating;
        private Integer leadTime;
    }

    @Setter
    @Getter
    public static class RawMaterialResponse {
        private Long id;
        private String name;
        private int quantity;
    }

}
