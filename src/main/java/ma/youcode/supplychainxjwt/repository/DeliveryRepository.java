package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
