package MicroServicio.Stock.infrastructure.jpa.adapter;


import MicroServicio.Stock.domain.exceptions.DuplicateArticleNameException;
import MicroServicio.Stock.domain.models.Article;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.domain.spi.IArticlePersistencePort;
import MicroServicio.Stock.infrastructure.exception.NoDataFoundException;
import MicroServicio.Stock.infrastructure.jpa.entity.ArticleEntity;
import MicroServicio.Stock.infrastructure.jpa.mapper.ArticleEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.IArticleRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.IBrandRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
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

    @Override
    public PageCustom<Article> getArticlesByPage(PageRequestCustom pageRequest, String brandName, String categoryName) {
        Sort sort = Sort.by(pageRequest.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, pageRequest.getSortField());
        Pageable pageable = (Pageable) PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), sort);

        Page<ArticleEntity> pageResult;

        if (brandName != null && !brandName.isEmpty() && categoryName != null && !categoryName.isEmpty()) {
            pageResult = articleRepository.findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(brandName, categoryName, pageable);
        } else if (brandName != null && !brandName.isEmpty()) {
            pageResult = articleRepository.findByBrandNameContainingIgnoreCase(brandName, pageable);
        } else if (categoryName != null && !categoryName.isEmpty()) {
            pageResult = articleRepository.findByCategoriesNameContainingIgnoreCase(categoryName, pageable);
        } else {
            pageResult = articleRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        }

        List<Article> articles = articleEntityMapper.toListArticle(pageResult.getContent());

        return new PageCustom<>(
                articles,
                (int) pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getNumber(),
                pageRequest.isAscending()
        );
    }

}
