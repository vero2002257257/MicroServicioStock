package MicroServicio.Stock.infrastructure.configuration;

import MicroServicio.Stock.domain.api.ICategoryServicePort;
import MicroServicio.Stock.domain.spi.ICategoryPersistencePort;
import MicroServicio.Stock.domain.usecase.CategoryUseCase;
import MicroServicio.Stock.infrastructure.jpa.adapter.CategoryJpaAdapter;
import MicroServicio.Stock.infrastructure.jpa.mapper.CategoryEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
}
