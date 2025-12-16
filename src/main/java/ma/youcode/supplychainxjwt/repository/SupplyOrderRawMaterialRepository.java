package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.SupplyOrderRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyOrderRawMaterialRepository extends JpaRepository<SupplyOrderRawMaterial, Long> {
}
