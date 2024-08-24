package MicroServicio.Stock.domain.spi;

import MicroServicio.Stock.domain.models.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    List<Category> GetAllCategories();
    Category getCategoryByName(String name);
    void updateCategory(Category category);
    void deleteCategory(String name);
}
