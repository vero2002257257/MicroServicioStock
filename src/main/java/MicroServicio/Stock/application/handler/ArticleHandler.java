package MicroServicio.Stock.application.handler;

import MicroServicio.Stock.application.dto.request.ArticleRequest;
import MicroServicio.Stock.application.dto.response.ArticleResponse;
import MicroServicio.Stock.application.handler.interfaces.IArticleHandler;
import MicroServicio.Stock.application.mapper.request.ArticleRequestMapper;
import MicroServicio.Stock.application.mapper.response.ArticleResponseMapper;
import MicroServicio.Stock.domain.api.IArticleServicePort;
import MicroServicio.Stock.domain.models.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ArticleHandler implements IArticleHandler {
    private final IArticleServicePort articleServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;

    @Override
    public void createArticle(ArticleRequest articleRequest) {
        Article article = articleRequestMapper.toArticle(articleRequest);
        articleServicePort.createArticle(article);
    }

    @Override
    public Optional<ArticleResponse> getArticleById(Long id) {
        return articleServicePort.getArticleById(id)
                .map(articleResponseMapper::toResponse);
    }

    @Override
    public List<ArticleResponse> getAllArticles() {
        return articleServicePort.getAllArticles().stream()
                .map(articleResponseMapper::toResponse)
                .toList();
    }
}
