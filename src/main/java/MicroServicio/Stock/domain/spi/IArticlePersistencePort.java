package MicroServicio.Stock.domain.spi;

import MicroServicio.Stock.domain.models.Article;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    Optional<Article> getArticleById(Long id);
    List<Article> getAllArticles();
}
