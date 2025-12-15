package ma.youcode.supplyChainX.service;

import jakarta.transaction.Transactional;
import ma.youcode.supplyChainX.model.SupplyOrderRawMaterial;
import ma.youcode.supplyChainX.repository.SupplyOrderRawMaterialRepository;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;

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
