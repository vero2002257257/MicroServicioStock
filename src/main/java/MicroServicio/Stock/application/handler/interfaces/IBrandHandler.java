package MicroServicio.Stock.application.handler.interfaces;

import MicroServicio.Stock.application.dto.request.BrandRequest;
import MicroServicio.Stock.application.dto.response.BrandResponse;
import MicroServicio.Stock.domain.models.Brand;
import MicroServicio.Stock.domain.pagination.PageCustom;
import MicroServicio.Stock.domain.pagination.PageRequestCustom;

import java.util.List;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandByName(String name);
    void updateBrand(BrandRequest brandRequest);
    void deleteBrand(String name);
    PageCustom<BrandResponse> getBrands(PageRequestCustom pageRequest);

    BrandResponse getBrandyByName(String name);
}
