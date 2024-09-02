package MicroServicio.Stock.domain.usecase;

import MicroServicio.Stock.domain.exceptions.InvalidBrandDataException;
import MicroServicio.Stock.domain.models.Brand;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBrand_validBrand_noExceptionThrown() {
        Brand brand = new Brand(1L,"Valid Brand Name", "Valid Description");

        assertDoesNotThrow(() -> brandUseCase.createBrand(brand));
        verify(brandPersistencePort, times(1)).createBrand(brand);
    }

    @Test
    void createBrand_nameTooLong_throwsInvalidBrandDataException() {
        Brand brand = new Brand(1L,"This is a very long brand name that exceeds fifty characters", "Valid Description");

        InvalidBrandDataException exception = assertThrows(
                InvalidBrandDataException.class,
                () -> brandUseCase.createBrand(brand)
        );

        assertEquals("El nombre de la marca no puede exceder los 50 caracteres.", exception.getMessage());
    }

    @Test
    void createBrand_descriptionTooLong_throwsInvalidBrandDataException() {
        Brand brand = new Brand(1L,"Valid Brand Name", "This description is too long and exceeds the limit of one hundred and twenty characters. This should throw an exception.");

        InvalidBrandDataException exception = assertThrows(
                InvalidBrandDataException.class,
                () -> brandUseCase.createBrand(brand)
        );

        assertEquals("La descripción de la marca no puede exceder los 120 caracteres.", exception.getMessage());
    }

    @Test
    void getAllBrands_returnsBrandList() {
        List<Brand> expectedBrands = Arrays.asList(
                new Brand(1L,"Brand 1", "Description 1"),
                new Brand(2L, "Brand 2", "Description 2")
        );

        when(brandPersistencePort.getAllBrands()).thenReturn(expectedBrands);

        List<Brand> result = brandUseCase.getAllBrands();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedBrands, result);
    }

    @Test
    void getBrandByName_existingBrand_returnsBrand() {
        String brandName = "Brand 1";
        Brand expectedBrand = new Brand(1L, brandName, "Description");

        when(brandPersistencePort.getBrandByName(brandName)).thenReturn(expectedBrand);

        Brand result = brandUseCase.getBrandByName(brandName);

        assertNotNull(result);
        assertEquals(expectedBrand, result);
    }

    @Test
    void updateBrand_validBrand_noExceptionThrown() {
        Brand brand = new Brand(1L,"Brand 1", "Updated Description");

        assertDoesNotThrow(() -> brandUseCase.updateBrand(brand));
        verify(brandPersistencePort, times(1)).updateBrand(brand);
    }

    @Test
    void deleteBrand_existingBrand_noExceptionThrown() {
        String brandName = "Brand 1";

        assertDoesNotThrow(() -> brandUseCase.deleteBrand(brandName));
        verify(brandPersistencePort, times(1)).deleteBrand(brandName);
    }

    @Test
    void testGetBrandsSuccess() {
        // Arrange
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10,true,"name"); // Asume que PageRequestCustom tiene un constructor adecuado
        PageCustom<Brand> expectedBrandsPage = new PageCustom<>(
                List.of(new Brand(1L, "Brand1", "descripcion"), new Brand(2L, "Brand2","descripcion")),
                pageRequest,
                2
        );

        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(expectedBrandsPage);

        // Act
        PageCustom<Brand> actualBrandsPage = brandUseCase.getBrands(pageRequest);

        // Assert
        assertNotNull(actualBrandsPage);
        assertEquals(2, actualBrandsPage.getTotalElements());
        verify(brandPersistencePort, times(1)).getBrands(pageRequest);
    }

    @Test
    void testGetBrandsNoBrandsFound() {
        // Arrange
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true,"name");
        PageCustom<Brand> emptyBrandsPage = new PageCustom<>(Collections.emptyList(), pageRequest, 0);

        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(emptyBrandsPage);

        // Act & Assert
        InvalidBrandDataException exception = assertThrows(InvalidBrandDataException.class, () -> {
            brandUseCase.getBrands(pageRequest);
        });

        assertEquals("No se encontraron marcas.", exception.getMessage());
        verify(brandPersistencePort, times(1)).getBrands(pageRequest);
    }

    @Test
    void testGetBrandsNullResponse() {
        // Arrange
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true,"name");

        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(null);

        // Act & Assert
        InvalidBrandDataException exception = assertThrows(InvalidBrandDataException.class, () -> {
            brandUseCase.getBrands(pageRequest);
        });

        assertEquals("No se encontraron marcas.", exception.getMessage());
        verify(brandPersistencePort, times(1)).getBrands(pageRequest);
    }
}
