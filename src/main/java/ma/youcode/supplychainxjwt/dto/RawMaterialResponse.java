package ma.youcode.supplychainxjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawMaterialResponse {
    private Long id;
    private String name;
    private int stock;
    private int minStock;
    private String unit;
}
