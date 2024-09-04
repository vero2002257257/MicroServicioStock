package MicroServicio.Stock.application.mapper.response;

import MicroServicio.Stock.application.dto.response.CategoryResponse;
import MicroServicio.Stock.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryResponse toResponse(Category category);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    List<CategoryResponse> toResponseList(List<Category> categories);

}