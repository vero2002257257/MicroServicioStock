package MicroServicio.Stock.application.handler.interfaces;

import MicroServicio.Stock.application.dto.request.ArticleRequest;
import MicroServicio.Stock.application.dto.response.ArticleResponse;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface IArticleHandler {
    void createArticle(ArticleRequest articleRequest);
    Optional<ArticleResponse> getArticleById(Long id);
    List<ArticleResponse> getAllArticles();
    PageCustom<ArticleResponse> getArticles(PageRequestCustom pageRequest, String brandName, String categoryName);
}
