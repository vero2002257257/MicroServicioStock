package MicroServicio.Stock.domain.api;

import MicroServicio.Stock.domain.models.Article;

import java.util.List;
import java.util.Optional;

public interface IArticleServicePort {
    void createArticle(Article article);
    Optional<Article> getArticleById(Long id);
    List<Article> getAllArticles();
}
