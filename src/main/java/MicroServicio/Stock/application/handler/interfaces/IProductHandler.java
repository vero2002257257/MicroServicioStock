package MicroServicio.Stock.application.handler.interfaces;

import MicroServicio.Stock.application.dto.request.ProductRequest;
import MicroServicio.Stock.application.dto.response.ProductResponse;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);
    Optional<ProductResponse> getProductById(Long id);
    List<ProductResponse> getAllProducts();
    PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest, String brandName, String categoryName);
}
