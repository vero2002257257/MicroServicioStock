package MicroServicio.Stock.application.mapper.response;

import MicroServicio.Stock.application.dto.response.ArticleResponse;
import MicroServicio.Stock.domain.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ArticleResponseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "cantity", target = "cantity")
    @Mapping(source = "price", target = "price")
    ArticleResponse toResponse(Article article);
    default List<ArticleResponse> toResponseList(List<Article> articleList) {
        return articleList.stream()
                .map(this::toResponse)
                .toList();
    }
}
