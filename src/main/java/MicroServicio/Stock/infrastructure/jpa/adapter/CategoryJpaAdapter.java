package MicroServicio.Stock.infrastructure.jpa.adapter;

import MicroServicio.Stock.infrastructure.exception.CategoryNotFoundException;
import MicroServicio.Stock.domain.exceptions.DuplicateCategoryNameException;
import MicroServicio.Stock.domain.models.Category;
import MicroServicio.Stock.domain.spi.ICategoryPersistencePort;
import MicroServicio.Stock.infrastructure.exception.NoDataFoundException;
import MicroServicio.Stock.infrastructure.jpa.entity.CategoryEntity;
import MicroServicio.Stock.infrastructure.jpa.mapper.CategoryEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new DuplicateCategoryNameException(category.getName());
        }
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public List<Category> GetAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toListCategory(categoryEntityList);
    }


    @Override
    public Category getCategoryByName(String name) {
        return categoryEntityMapper.toCategory(
                categoryRepository.findByName(name)
                        .orElseThrow(CategoryNotFoundException::new)
        );
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public void deleteCategory(String name) {
        categoryRepository.deleteByName(name);
    }
}
