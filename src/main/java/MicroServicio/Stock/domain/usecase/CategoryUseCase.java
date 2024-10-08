package MicroServicio.Stock.domain.usecase;

import MicroServicio.Stock.domain.api.ICategoryServicePort;
import MicroServicio.Stock.domain.exceptions.InvalidCategoryDataException;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import MicroServicio.Stock.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {


    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {
        // Validación: El nombre no debe exceder 50 caracteres
        if (category.getName().length() > 50 ) {
            throw new InvalidCategoryDataException("El nombre de la categoría no puede exceder los 50 caracteres.");
        }

        // Validación: La descripción no debe exceder 90 caracteres
        if (category.getDescription().length() > 90) {
            throw new InvalidCategoryDataException("La descripción de la categoría no puede exceder los 90 caracteres.");
        }
        // Guardar la categoría si todas las validaciones pasan
        categoryPersistencePort.createCategory(category);

    }

    @Override
    public List<Category> GetAllCategories() {
        return categoryPersistencePort.GetAllCategories();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryPersistencePort.getCategoryByName(name);
    }


    @Override
    public void updateCategory(Category category) {
        categoryPersistencePort.updateCategory(category);

    }

    @Override
    public void deleteCategory(String name) {
        categoryPersistencePort.deleteCategory(name);

    }

    @Override
    public PageCustom<Category> getCategories(PageRequestCustom pageRequest) {
        // Llamar al puerto de persistencia para obtener las categorías paginadas
        PageCustom<Category> categoriesPage = categoryPersistencePort.getCategories(pageRequest);

        // Validar que la respuesta no sea nula
        if (categoriesPage == null || categoriesPage.getContent().isEmpty()) {
            throw new InvalidCategoryDataException("No se encontraron categorías.");
        }

        return categoriesPage;
    }

}
