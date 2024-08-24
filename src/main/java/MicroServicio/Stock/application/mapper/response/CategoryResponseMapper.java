package MicroServicio.Stock.application.mapper.response;

import MicroServicio.Stock.application.dto.response.CategoryResponse;
import MicroServicio.Stock.domain.models.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toResponseList(List<Category> categories);
}