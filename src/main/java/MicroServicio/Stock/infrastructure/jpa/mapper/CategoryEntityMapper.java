package MicroServicio.Stock.infrastructure.jpa.mapper;

import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.infrastructure.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntity toEntity(Category category);
    Category toCategory(CategoryEntity categoryEntity);
    List<Category> toListCategory(List<CategoryEntity> categoryEntityList);
}