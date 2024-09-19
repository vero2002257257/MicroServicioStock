package MicroServicio.Stock.domain.spi;

import MicroServicio.Stock.domain.models.Product;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface IProductPersistencePort {
    void createProduct(Product product);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    PageCustom<Product> getProductsByPage(PageRequestCustom pageRequest, String brandName, String categoryName);
}
