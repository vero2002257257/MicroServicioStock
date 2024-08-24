package MicroServicio.Stock.application.handler;

import MicroServicio.Stock.application.dto.request.CategoryRequest;
import MicroServicio.Stock.application.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> GetAllCategories();
    CategoryResponse getCategoryByName(String name);
    void updateCategory(CategoryRequest categoryRequest);
    void deleteCategory(String name);
}
