package ma.youcode.supplychainxjwt.repository;

import ma.youcode.supplychainxjwt.model.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    List<RawMaterial> findByName(String name);

    boolean existsByName(String name);
}
