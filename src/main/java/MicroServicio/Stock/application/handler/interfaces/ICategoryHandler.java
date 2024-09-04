package MicroServicio.Stock.application.handler.interfaces;

import MicroServicio.Stock.application.dto.request.CategoryRequest;
import MicroServicio.Stock.application.dto.response.CategoryResponse;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> GetAllCategories();
    CategoryResponse getCategoryByName(String name);
    void updateCategory(CategoryRequest categoryRequest);
    void deleteCategory(String name);
    PageCustom<CategoryResponse> getCategories(PageRequestCustom pageRequest);
}
