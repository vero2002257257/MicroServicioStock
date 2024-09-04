package MicroServicio.Stock.domain.spi;

import MicroServicio.Stock.domain.models.Article;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    Optional<Article> getArticleById(Long id);
    List<Article> getAllArticles();
    PageCustom<Article> getArticlesByPage(PageRequestCustom pageRequest, String brandName, String categoryName);
}
