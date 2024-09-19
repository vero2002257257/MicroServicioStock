package MicroServicio.Stock.application.handler;

import MicroServicio.Stock.application.dto.request.ProductRequest;
import MicroServicio.Stock.application.dto.response.ProductResponse;
import MicroServicio.Stock.application.handler.interfaces.IProductHandler;
import MicroServicio.Stock.application.mapper.request.ProductRequestMapper;
import MicroServicio.Stock.application.mapper.response.ProductResponseMapper;
import MicroServicio.Stock.domain.api.IProductServicePort;
import MicroServicio.Stock.domain.models.Product;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        productServicePort.createProduct(product);
    }

    @Override
    public Optional<ProductResponse> getProductById(Long id) {
        return productServicePort.getProductById(id)
                .map(productResponseMapper::toResponse);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productServicePort.getAllProducts().stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }
    @Override
    public PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest, String brandName, String categoryName) {

        PageCustom<Product> productsPage = productServicePort.getProductsByPage(pageRequest, brandName, categoryName);

        // Transformar a ProductResponse
        List<ProductResponse> productRespons = productResponseMapper.toResponseList(productsPage.getContent());
        return new PageCustom<>(
                productRespons,
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.getCurrentPage(),
                productsPage.isAscending()
        );
    }

}
