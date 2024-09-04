package MicroServicio.Stock.infrastructure.jpa.adapter;


import MicroServicio.Stock.domain.exceptions.DuplicateArticleNameException;
import MicroServicio.Stock.domain.models.Article;
import MicroServicio.Stock.domain.spi.IArticlePersistencePort;
import MicroServicio.Stock.infrastructure.exception.NoDataFoundException;
import MicroServicio.Stock.infrastructure.jpa.entity.ArticleEntity;
import MicroServicio.Stock.infrastructure.jpa.mapper.ArticleEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.IArticleRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.IBrandRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    public void createArticle(Article article) {
        if(articleRepository.findByName(article.getName()).isPresent()){
            throw new DuplicateArticleNameException(article.getName());
        }

        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        articleRepository.save(articleEntity);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(articleEntityMapper::toArticle);
    }

    @Override
    public List<Article> getAllArticles() {
        List <ArticleEntity> articleEntityList = articleRepository.findAll();
        if(articleEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return articleEntityMapper.toListArticle(articleEntityList);
    }
}
