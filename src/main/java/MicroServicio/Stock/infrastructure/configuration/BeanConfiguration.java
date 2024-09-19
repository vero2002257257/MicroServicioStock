package MicroServicio.Stock.infrastructure.configuration;

import MicroServicio.Stock.domain.api.IProductServicePort;
import MicroServicio.Stock.domain.api.IBrandServicePort;
import MicroServicio.Stock.domain.api.ICategoryServicePort;
import MicroServicio.Stock.domain.spi.IProductPersistencePort;
import MicroServicio.Stock.domain.spi.IBrandPersistencePort;
import MicroServicio.Stock.domain.spi.ICategoryPersistencePort;
import MicroServicio.Stock.domain.usecase.ProductUseCase;
import MicroServicio.Stock.domain.usecase.BrandUseCase;
import MicroServicio.Stock.domain.usecase.CategoryUseCase;
import MicroServicio.Stock.infrastructure.jpa.adapter.ProductJpaAdapter;
import MicroServicio.Stock.infrastructure.jpa.adapter.BrandJpaAdapter;
import MicroServicio.Stock.infrastructure.jpa.adapter.CategoryJpaAdapter;
import MicroServicio.Stock.infrastructure.jpa.mapper.ProductEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.mapper.BrandEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.mapper.CategoryEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.IProductRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.IBrandRepository;
import MicroServicio.Stock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;
    private final IProductRepository iProductRepository;
    private final ProductEntityMapper productEntityMapper;
    private final UserDetailsService userDetailsService;


    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductJpaAdapter(iProductRepository, productEntityMapper, brandRepository, categoryRepository);
    }

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort());
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}