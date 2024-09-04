package MicroServicio.Stock.domain.usecase;

import MicroServicio.Stock.domain.exceptions.*;
import MicroServicio.Stock.domain.models.Article;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.domain.spi.IArticlePersistencePort;
import MicroServicio.Stock.infrastructure.exception.ArticleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort iArticlePersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createArticle_ShouldThrowInvalidCategoryDataException_WhenCategoriesAreNull() {
        Article article = new Article();
        article.setCategories(null);

        InvalidCategoryDataException exception = assertThrows(InvalidCategoryDataException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo debe tener al menos una categoría.", exception.getMessage());
        verify(iArticlePersistencePort, never()).createArticle(any());
    }

    @Test
    void createArticle_ShouldThrowInvalidCategoryDataException_WhenCategoriesAreEmpty() {
        Article article = new Article();
        article.setCategories(Arrays.asList());

        InvalidCategoryDataException exception = assertThrows(InvalidCategoryDataException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo debe tener al menos una categoría.", exception.getMessage());
        verify(iArticlePersistencePort, never()).createArticle(any());
    }

    @Test
    void createArticle_ShouldThrowInvalidCategoryDataException_WhenMoreThanThreeCategories() {
        Article article = new Article();
        article.setCategories(Arrays.asList(new Category(), new Category(), new Category(), new Category()));

        InvalidCategoryDataException exception = assertThrows(InvalidCategoryDataException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo no puede tener más de 3 categorías.", exception.getMessage());
        verify(iArticlePersistencePort, never()).createArticle(any());
    }

    @Test
    void createArticle_ShouldThrowDuplicateCategoryNameException_WhenCategoriesAreDuplicated() {
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = category1; // Categoría duplicada

        Article article = new Article();
        article.setCategories(Arrays.asList(category1, category2, category3));

        DuplicateCategoryNameException exception = assertThrows(DuplicateCategoryNameException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo no puede tener categorías duplicadas.", exception.getMessage());
        verify(iArticlePersistencePort, never()).createArticle(any());
    }

    @Test
    void createArticle_ShouldCallPersistencePort_WhenValidArticle() {
        Category category1 = new Category();
        Category category2 = new Category();

        Article article = new Article();
        article.setCategories(Arrays.asList(category1, category2));

        articleUseCase.createArticle(article);

        verify(iArticlePersistencePort, times(1)).createArticle(article);
    }

    @Test
    void getArticleById_ShouldReturnArticle_WhenArticleExists() {
        Long articleId = 1L;
        Article article = new Article();
        when(iArticlePersistencePort.getArticleById(articleId)).thenReturn(Optional.of(article));

        Optional<Article> result = articleUseCase.getArticleById(articleId);

        assertTrue(result.isPresent());
        assertEquals(article, result.get());
    }

    @Test
    void getArticleById_ShouldReturnEmpty_WhenArticleDoesNotExist() {
        Long articleId = 1L;
        when(iArticlePersistencePort.getArticleById(articleId)).thenReturn(Optional.empty());

        Optional<Article> result = articleUseCase.getArticleById(articleId);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllArticles_ShouldReturnListOfArticles() {
        Article article1 = new Article();
        Article article2 = new Article();
        List<Article> articles = Arrays.asList(article1, article2);
        when(iArticlePersistencePort.getAllArticles()).thenReturn(articles);

        List<Article> result = articleUseCase.getAllArticles();

        assertEquals(2, result.size());
        assertEquals(articles, result);
    }
    @Test
    void getArticlesByPage_WhenPageIsEmptyAndBrandAndCategoryAreProvided_ShouldThrowArticleNotFoundForBrandAndCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iArticlePersistencePort.getArticlesByPage(pageRequest, "Nike", "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundForBrandAndCategoryException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, "Nike", "Shoes"));
    }

    @Test
    void getArticlesByPage_WhenPageIsEmptyAndBrandIsProvided_ShouldThrowArticleNotFoundForBrandException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iArticlePersistencePort.getArticlesByPage(pageRequest, "Nike", null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundForBrandException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, "Nike", null));
    }

    @Test
    void getArticlesByPage_WhenPageIsEmptyAndCategoryIsProvided_ShouldThrowArticleNotFoundForCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iArticlePersistencePort.getArticlesByPage(pageRequest, null, "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundForCategoryException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, null, "Shoes"));
    }

    @Test
    void getArticlesByPage_WhenPageIsEmptyAndNoBrandOrCategoryProvided_ShouldThrowArticleNotFoundException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iArticlePersistencePort.getArticlesByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));
        when(iArticlePersistencePort.getArticlesByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, null, null));
    }

    @Test
    void getArticlesByPage_WhenArticlesArePresent_ShouldFilterCategoriesAndReturnPage() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        Article article = new Article();
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        article.setCategories(List.of(category));

        PageCustom<Article> expectedPage = new PageCustom<>(List.of(article), 1, 10, 1, true);
        when(iArticlePersistencePort.getArticlesByPage(pageRequest, null, null))
                .thenReturn(expectedPage);

        PageCustom<Article> result = articleUseCase.getArticlesByPage(pageRequest, null, null);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getCategories().get(0).getId());
        assertEquals("Electronics", result.getContent().get(0).getCategories().get(0).getName());
        assertEquals(null, result.getContent().get(0).getCategories().get(0).getDescription());

        verify(iArticlePersistencePort, times(1)).getArticlesByPage(pageRequest, null, null);
    }
}
