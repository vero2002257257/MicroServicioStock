package MicroServicio.Stock.application.handler.interfaces;

import MicroServicio.Stock.application.dto.request.BrandRequest;
import MicroServicio.Stock.application.dto.response.BrandResponse;
import MicroServicio.Stock.domain.models.Brand;

import java.util.List;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandByName(String name);
    void updateBrand(BrandRequest brandRequest);
    void deleteBrand(String name);

}
