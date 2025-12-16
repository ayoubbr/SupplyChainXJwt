package ma.youcode.supplychainxjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
}
