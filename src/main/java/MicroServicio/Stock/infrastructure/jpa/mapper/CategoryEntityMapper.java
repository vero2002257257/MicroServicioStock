package MicroServicio.Stock.infrastructure.jpa.mapper;

import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.infrastructure.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryEntity toEntity(Category category);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category toCategory(CategoryEntity categoryEntity);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    List<Category> toListCategory(List<CategoryEntity> categoryEntityList);
}