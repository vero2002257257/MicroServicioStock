package MicroServicio.Stock.domain.api;

import MicroServicio.Stock.domain.models.Category;

import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
    List<Category> GetAllCategories();
    Category getCategoryByName(String name);
    void updateCategory(Category category);
    void deleteCategory(String name);
}
