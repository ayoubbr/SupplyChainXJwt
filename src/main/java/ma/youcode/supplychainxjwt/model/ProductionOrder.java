package ma.youcode.supplychainxjwt.model;

import jakarta.persistence.*;
import ma.youcode.supplychainxjwt.shared.enums.ProductionOrderStatus;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "production_orders")
public class ProductionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductionOrderStatus status;

    private LocalDate startDate;
    private LocalDate endDate;
}

