package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {
}
