package MicroServicio.Stock.infrastructure.jpa.adapter;

import MicroServicio.Stock.infrastructure.exception.DuplicateBrandNameException;
import MicroServicio.Stock.domain.models.Brand;
import MicroServicio.Stock.domain.spi.IBrandPersistencePort;
import MicroServicio.Stock.infrastructure.exception.CategoryNotFoundException;
import MicroServicio.Stock.infrastructure.exception.NoDataFoundException;
import MicroServicio.Stock.infrastructure.jpa.entity.BrandEntity;
import MicroServicio.Stock.infrastructure.jpa.mapper.BrandEntityMapper;
import MicroServicio.Stock.infrastructure.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public void createBrand(Brand brand) {
        if (brandRepository.findByName(brand.getName()).isPresent()) {
            throw new DuplicateBrandNameException(brand.getName());
        }
        BrandEntity brandEntity = brandEntityMapper.toBrand(brand);
        brandRepository.save(brandEntity);
    }

    @Override
    public List<Brand> getAllBrands() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        if (brandEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return brandEntityMapper.toListBrand(brandEntityList);
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandEntityMapper.toBrand(
                brandRepository.findByName(name)
                        .orElseThrow(CategoryNotFoundException::new)
        );
    }

    @Override
    public void updateBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toBrand(brand));
    }

    @Override
    public void deleteBrand(String name) {
        brandRepository.deleteByName(name);
    }
}
