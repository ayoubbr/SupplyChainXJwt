package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.BillOfMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillOfMaterialRepository extends JpaRepository<BillOfMaterial, Long> {
    List<BillOfMaterial> findByProductId(Long id);

    BillOfMaterial findByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);
}
