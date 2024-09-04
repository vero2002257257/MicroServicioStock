package MicroServicio.Stock.application.mapper.request;

import MicroServicio.Stock.domain.models.Brand;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.models.Article;
import MicroServicio.Stock.application.dto.request.ArticleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArticleRequestMapper {
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "toBrand")
    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "toCategories")
    Article toArticle(ArticleRequest request);

    @Named("toBrand")
    default Brand toBrand(Long brandId) {
        if (brandId == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setId(brandId);
        return brand;
    }

    @Named("toCategories")
    default List<Category> toCategories(Set<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return categoryIds.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toList());
    }
}
