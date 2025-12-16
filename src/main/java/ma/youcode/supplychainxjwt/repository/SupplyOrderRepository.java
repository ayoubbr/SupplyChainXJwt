package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long> {
}
