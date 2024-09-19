package MicroServicio.Stock.application.dto.response;

import MicroServicio.Stock.domain.models.Brand;
import MicroServicio.Stock.domain.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private String name;
    private String description;
    private int cantity;
    private double price;
    private Brand brand;
    private List<Category> categories;
}
