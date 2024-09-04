package MicroServicio.Stock.application.mapper.request;

import MicroServicio.Stock.application.dto.request.BrandRequest;
import MicroServicio.Stock.domain.models.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface BrandRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand toBrand(BrandRequest brandRequest);
}
