package MicroServicio.Stock.application.handler;

import MicroServicio.Stock.application.dto.request.CategoryRequest;
import MicroServicio.Stock.application.dto.response.CategoryResponse;
import MicroServicio.Stock.application.handler.interfaces.ICategoryHandler;
import MicroServicio.Stock.application.mapper.request.CategoryRequestMapper;
import MicroServicio.Stock.application.mapper.response.CategoryResponseMapper;
import MicroServicio.Stock.domain.api.ICategoryServicePort;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.createCategory(category);
    }

    @Override
    public List<CategoryResponse> GetAllCategories() {
        return categoryResponseMapper.toResponseList(categoryServicePort.GetAllCategories());
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryServicePort.getCategoryByName(name);
        return categoryResponseMapper.toResponse(category);
    }

    @Override
    public void updateCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.updateCategory(category);
    }

    @Override
    public void deleteCategory(String name) {
        Category category = categoryServicePort.getCategoryByName(name);
        categoryServicePort.deleteCategory(name);
    }

    @Override
    public PageCustom<CategoryResponse> getCategories(PageRequestCustom pageRequest) {
        PageCustom<Category> categoriesPage = categoryServicePort.getCategories(pageRequest);
        List<CategoryResponse> responseList = categoryResponseMapper.toResponseList(categoriesPage.getContent());
        return new PageCustom<>(
                responseList,
                categoriesPage.getTotalElements(),
                categoriesPage.getTotalPages(),
                categoriesPage.getCurrentPage(),
                categoriesPage.isAscending()
        );
    }
}
