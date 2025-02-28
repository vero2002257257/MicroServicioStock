package MicroServicio.Stock.domain.usecase;

import MicroServicio.Stock.domain.exceptions.*;
import MicroServicio.Stock.domain.models.Product;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.domain.spi.IProductPersistencePort;
import MicroServicio.Stock.infrastructure.exception.ProductNotFoundException;
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

class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort iProductPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_ShouldThrowInvalidCategoryDataException_WhenCategoriesAreNull() {
        Product product = new Product();
        product.setCategories(null);

        InvalidCategoryDataException exception = assertThrows(InvalidCategoryDataException.class, () -> {
            productUseCase.createProduct(product);
        });

        assertEquals("El producto debe tener al menos una categoría.", exception.getMessage());
        verify(iProductPersistencePort, never()).createProduct(any());
    }

    @Test
    void createProduct_ShouldThrowInvalidCategoryDataException_WhenCategoriesAreEmpty() {
        Product product = new Product();
        product.setCategories(Arrays.asList());

        InvalidCategoryDataException exception = assertThrows(InvalidCategoryDataException.class, () -> {
            productUseCase.createProduct(product);
        });

        assertEquals("El producto debe tener al menos una categoría.", exception.getMessage());
        verify(iProductPersistencePort, never()).createProduct(any());
    }

    @Test
    void createProduct_ShouldThrowInvalidCategoryDataException_WhenMoreThanThreeCategories() {
        Product product = new Product();
        product.setCategories(Arrays.asList(new Category(), new Category(), new Category(), new Category()));

        InvalidCategoryDataException exception = assertThrows(InvalidCategoryDataException.class, () -> {
            productUseCase.createProduct(product);
        });

        assertEquals("El producto no puede tener más de 3 categorías.", exception.getMessage());
        verify(iProductPersistencePort, never()).createProduct(any());
    }

    @Test
    void createProduct_ShouldThrowDuplicateCategoryNameException_WhenCategoriesAreDuplicated() {
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = category1; // Categoría duplicada

        Product product = new Product();
        product.setCategories(Arrays.asList(category1, category2, category3));

        DuplicateCategoryNameException exception = assertThrows(DuplicateCategoryNameException.class, () -> {
            productUseCase.createProduct(product);
        });

        assertEquals("El producto no puede tener categorías duplicadas.", exception.getMessage());
        verify(iProductPersistencePort, never()).createProduct(any());
    }

    @Test
    void createProduct_ShouldCallPersistencePort_WhenValidProduct() {
        Category category1 = new Category();
        Category category2 = new Category();

        Product product = new Product();
        product.setCategories(Arrays.asList(category1, category2));

        productUseCase.createProduct(product);

        verify(iProductPersistencePort, times(1)).createProduct(product);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        Long productId = 1L;
        Product product = new Product();
        when(iProductPersistencePort.getProductById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productUseCase.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void getProductById_ShouldReturnEmpty_WhenProductDoesNotExist() {
        Long productId = 1L;
        when(iProductPersistencePort.getProductById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productUseCase.getProductById(productId);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllProduct_ShouldReturnListOfProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(iProductPersistencePort.getAllProducts()).thenReturn(products);

        List<Product> result = productUseCase.getAllProducts();

        assertEquals(2, result.size());
        assertEquals(products, result);
    }
    @Test
    void getProductsByPage_WhenPageIsEmptyAndBrandAndCategoryAreProvided_ShouldThrowProductNotFoundForBrandAndCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iProductPersistencePort.getProductsByPage(pageRequest, "Nike", "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundForBrandAndCategoryException.class, () ->
                productUseCase.getProductsByPage(pageRequest, "Nike", "Shoes"));
    }

    @Test
    void getProductsByPage_WhenPageIsEmptyAndBrandIsProvided_ShouldThrowProductNotFoundForBrandException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iProductPersistencePort.getProductsByPage(pageRequest, "Nike", null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundForBrandException.class, () ->
                productUseCase.getProductsByPage(pageRequest, "Nike", null));
    }

    @Test
    void getProductsByPage_WhenPageIsEmptyAndCategoryIsProvided_ShouldThrowProductNotFoundForCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iProductPersistencePort.getProductsByPage(pageRequest, null, "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundForCategoryException.class, () ->
                productUseCase.getProductsByPage(pageRequest, null, "Shoes"));
    }

    @Test
    void getProductsByPage_WhenPageIsEmptyAndNoBrandOrCategoryProvided_ShouldThrowProductNotFoundException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(iProductPersistencePort.getProductsByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));
        when(iProductPersistencePort.getProductsByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundException.class, () ->
                productUseCase.getProductsByPage(pageRequest, null, null));
    }

    @Test
    void getProductsByPage_WhenProductsArePresent_ShouldFilterCategoriesAndReturnPage() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        Product product = new Product();
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        product.setCategories(List.of(category));

        PageCustom<Product> expectedPage = new PageCustom<>(List.of(product), 1, 10, 1, true);
        when(iProductPersistencePort.getProductsByPage(pageRequest, null, null))
                .thenReturn(expectedPage);

        PageCustom<Product> result = productUseCase.getProductsByPage(pageRequest, null, null);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getCategories().get(0).getId());
        assertEquals("Electronics", result.getContent().get(0).getCategories().get(0).getName());
        assertEquals(null, result.getContent().get(0).getCategories().get(0).getDescription());

        verify(iProductPersistencePort, times(1)).getProductsByPage(pageRequest, null, null);
    }
}
