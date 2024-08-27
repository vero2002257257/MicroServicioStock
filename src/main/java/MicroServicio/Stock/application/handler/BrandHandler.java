package MicroServicio.Stock.application.handler;

import MicroServicio.Stock.application.dto.request.BrandRequest;
import MicroServicio.Stock.application.dto.response.BrandResponse;
import MicroServicio.Stock.application.handler.interfaces.IBrandHandler;
import MicroServicio.Stock.application.mapper.request.BrandRequestMapper;
import MicroServicio.Stock.application.mapper.response.BrandResponseMapper;
import MicroServicio.Stock.domain.api.IBrandServicePort;
import MicroServicio.Stock.domain.models.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandHandler implements IBrandHandler {
    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;


    @Override
    public void createBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.createBrand(brand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandResponseMapper.toBrandResponses(brandServicePort.getAllBrands());
    }

    @Override
    public BrandResponse getBrandByName(String name) {
        Brand brand = brandServicePort.getBrandByName(name);
        return brandResponseMapper.toResponse(brand);
    }

    @Override
    public void updateBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.updateBrand(brand);
    }

    @Override
    public void deleteBrand(String name) {
        brandServicePort.deleteBrand(name);
    }
}
