package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);

    boolean existsByName(String name);

    boolean existsByContact(String contact);
}
