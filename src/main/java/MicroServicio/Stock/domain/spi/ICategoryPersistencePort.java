package MicroServicio.Stock.domain.spi;

import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    List<Category> GetAllCategories();
    Category getCategoryByName(String name);
    void updateCategory(Category category);
    void deleteCategory(String name);
    PageCustom<Category> getCategories(PageRequestCustom pageRequest);
}
