package MicroServicio.Stock.application.handler.interfaces;

import MicroServicio.Stock.application.dto.request.ArticleRequest;
import MicroServicio.Stock.application.dto.response.ArticleResponse;

import java.util.List;
import java.util.Optional;

public interface IArticleHandler {
    void createArticle(ArticleRequest articleRequest);
    Optional<ArticleResponse> getArticleById(Long id);
    List<ArticleResponse> getAllArticles();
}
