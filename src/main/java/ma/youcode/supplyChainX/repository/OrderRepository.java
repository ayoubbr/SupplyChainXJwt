package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
