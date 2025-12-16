package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    boolean existsByName(String name);
}
