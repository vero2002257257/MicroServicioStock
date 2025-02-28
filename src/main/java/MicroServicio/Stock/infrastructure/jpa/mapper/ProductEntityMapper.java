package MicroServicio.Stock.infrastructure.jpa.mapper;

import MicroServicio.Stock.domain.models.Product;
import MicroServicio.Stock.infrastructure.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ProductEntityMapper {
    @Mapping(target = "id", ignore = true)
    ProductEntity toEntity(Product product);
    Product toProduct(ProductEntity productEntity);
    List<Product> toListProduct(List<ProductEntity> productEntityList);
}
