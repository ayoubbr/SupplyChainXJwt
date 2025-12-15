package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);

    boolean existsByName(String name);

    boolean existsByContact(String contact);
}
