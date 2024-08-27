package MicroServicio.Stock.application.mapper.request;

import MicroServicio.Stock.application.dto.request.BrandRequest;
import MicroServicio.Stock.domain.models.Brand;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface BrandRequestMapper {
    Brand toBrand(BrandRequest brandRequest);
}
