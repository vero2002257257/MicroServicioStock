package MicroServicio.Stock.infrastructure.jpa.repository;


import MicroServicio.Stock.infrastructure.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    void deleteByName(String name);
}
