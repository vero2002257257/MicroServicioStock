package MicroServicio.Stock.application.mapper.response;

import MicroServicio.Stock.application.dto.response.BrandResponse;
import MicroServicio.Stock.domain.models.Brand;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper (componentModel = "spring")
public interface BrandResponseMapper {
    BrandResponse toResponse(Brand brand);
    List<BrandResponse> toBrandResponses(List<Brand> brands);
}
