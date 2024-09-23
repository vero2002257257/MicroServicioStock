package MicroServicio.Stock.domain.usecase;

import MicroServicio.Stock.domain.api.IProductServicePort;
import MicroServicio.Stock.domain.exceptions.*;
import MicroServicio.Stock.domain.models.Product;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.domain.spi.IProductPersistencePort;
import MicroServicio.Stock.infrastructure.exception.ProductNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static MicroServicio.Stock.utils.Constants.PRODUCT_NOT_FOUND;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort iProductPersistencePort;

    public ProductUseCase(IProductPersistencePort iProductPersistencePort) {
        this.iProductPersistencePort = iProductPersistencePort;
    }

    @Override
    public void createProduct(Product product) {
        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            throw new InvalidCategoryDataException("El producto debe tener al menos una categoría.");
        }

        if (product.getCategories().size() > 3) {
            throw new InvalidCategoryDataException("El Producto no puede tener más de 3 categorías.");
        }

        // Convertir la lista a un Set para verificar duplicados
        Set<Category> uniqueCategories = new HashSet<>(product.getCategories());
        if (uniqueCategories.size() < product.getCategories().size()) {
            throw new DuplicateCategoryNameException("El Producto no puede tener categorías duplicadas.");
        }

        // Si pasa todas las validaciones, se crea el Producto
        iProductPersistencePort.createProduct(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return iProductPersistencePort.getProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return iProductPersistencePort.getAllProducts();
    }
    @Override
    public PageCustom<Product> getProductsByPage(PageRequestCustom pageRequest, String brandName, String categoryName) {
        PageCustom<Product> productsPage = iProductPersistencePort.getProductsByPage(pageRequest, brandName, categoryName);

        // Verificar si la página de productos está vacía
        if (productsPage.getContent().isEmpty()) {
            if (brandName != null && !brandName.isEmpty() && categoryName != null && !categoryName.isEmpty()) {
                throw new ProductNotFoundForBrandAndCategoryException(
                        "No hay productos encontrados con la marca: " + brandName + " y la categoría: " + categoryName);
            } else if (brandName != null && !brandName.isEmpty()) {
                throw new ProductNotFoundForBrandException(
                        "No hay productos encontrados con la marca: " + brandName);
            } else if (categoryName != null && !categoryName.isEmpty()) {
                throw new ProductNotFoundForCategoryException(
                        "No hay productos encontrados con la categoría: " + categoryName);
            } else {
                throw new ProductNotFoundException("No se encontraron productos.");
            }
        }

        // Filtrar categorías para incluir solo id y nombre
        productsPage.getContent().forEach(product -> {
            List<Category> categories = product.getCategories().stream()
                    .map(category -> new Category(category.getId(), category.getName(), null)) // Excluir descripción
                    .toList();
            product.setCategories(categories);
        });

        return productsPage;
    }

    @Override
    public void updateQuantity(Long id, int quantity) {
        if(!iProductPersistencePort.getProductById(id).isPresent()) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        iProductPersistencePort.updateQuantity(id, quantity);
    }
}
