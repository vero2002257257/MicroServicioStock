package MicroServicio.Stock.application.mapper.request;

import MicroServicio.Stock.application.dto.request.CategoryRequest;
import MicroServicio.Stock.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category toCategory(CategoryRequest categoryRequest);
}