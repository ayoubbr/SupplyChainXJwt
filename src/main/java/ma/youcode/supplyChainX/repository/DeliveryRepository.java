package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
