package MicroServicio.Stock.infrastructure.configuration;

import MicroServicio.Stock.domain.api.IBrandServicePort;
import MicroServicio.Stock.domain.api.ICategoryServicePort;
import MicroServicio.Stock.domain.spi.IBrandPersistencePort;
import MicroServicio.Stock.domain.spi.ICategoryPersistencePort;
import MicroServicio.Stock.domain.usecase.BrandUseCase;
import MicroServicio.Stock.domain.usecase.CategoryUseCase;
import MicroServicio.Stock.infrastructure.jpa.adapter.BrandJpaAdapter;
import MicroServicio.Stock.infrastructure.jpa.adapter.CategoryJpaAdapter;
import MicroServicio.Stock.infrastructure.jpa.mapper.BrandEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.mapper.CategoryEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.IBrandRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort () {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }

}
