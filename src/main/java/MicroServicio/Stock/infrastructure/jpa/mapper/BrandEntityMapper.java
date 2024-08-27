package MicroServicio.Stock.infrastructure.jpa.mapper;

import MicroServicio.Stock.domain.models.Brand;
import MicroServicio.Stock.infrastructure.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {
    BrandEntity toBrand(Brand brand);
    Brand toBrand(BrandEntity brandEntity);
    List<Brand> toListBrand(List<BrandEntity> brandEntityList);
}
