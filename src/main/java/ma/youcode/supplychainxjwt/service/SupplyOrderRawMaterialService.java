package ma.youcode.supplychainxjwt.service;

import jakarta.transaction.Transactional;
import ma.youcode.supplychainxjwt.model.SupplyOrderRawMaterial;
import ma.youcode.supplychainxjwt.repository.SupplyOrderRawMaterialRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SupplyOrderRawMaterialService {

    private final SupplyOrderRawMaterialRepository supplyOrderRawMaterialRepository;

    public SupplyOrderRawMaterialService(SupplyOrderRawMaterialRepository supplyOrderRawMaterialRepository) {
        this.supplyOrderRawMaterialRepository = supplyOrderRawMaterialRepository;
    }

    public SupplyOrderRawMaterial save(SupplyOrderRawMaterial supplyOrderRawMaterial) {
        return supplyOrderRawMaterialRepository.save(supplyOrderRawMaterial);
    }
}
