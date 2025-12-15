package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.SupplyOrderRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyOrderRawMaterialRepository extends JpaRepository<SupplyOrderRawMaterial, Long> {
}
