package MicroServicio.Stock.infrastructure.jpa.repository;

import MicroServicio.Stock.infrastructure.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
    Page<ProductEntity> findByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);
    Page<ProductEntity> findByCategoriesNameContainingIgnoreCase(String categoryName, Pageable pageable);
    Page<ProductEntity> findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(String brandName, String categoryName, Pageable pageable);
}