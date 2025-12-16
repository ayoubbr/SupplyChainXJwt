package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
