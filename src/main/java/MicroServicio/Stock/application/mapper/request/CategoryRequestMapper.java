package MicroServicio.Stock.application.mapper.request;

import MicroServicio.Stock.application.dto.request.CategoryRequest;
import MicroServicio.Stock.domain.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);
}