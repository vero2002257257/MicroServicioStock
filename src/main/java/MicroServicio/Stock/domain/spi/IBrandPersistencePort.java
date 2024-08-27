package MicroServicio.Stock.domain.spi;

import MicroServicio.Stock.domain.models.Brand;

import java.util.List;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    List<Brand> getAllBrands();
    Brand getBrandByName(String name);
    void updateBrand(Brand brand);
    void deleteBrand(String name);
}
