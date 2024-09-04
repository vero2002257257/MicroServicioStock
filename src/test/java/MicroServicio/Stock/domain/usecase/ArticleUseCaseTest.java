package MicroServicio.Stock.domain.usecase;

import MicroServicio.Stock.domain.exceptions.DuplicateCategoryNameException;
import MicroServicio.Stock.domain.exceptions.InvalidCategoryDataException;
import MicroServicio.Stock.domain.models.Article;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.spi.IArticlePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
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
}
