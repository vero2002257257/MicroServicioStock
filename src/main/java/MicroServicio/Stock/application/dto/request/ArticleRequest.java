package MicroServicio.Stock.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ArticleRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Min(1)
    private int cantity;
    @NotBlank
    @Positive(message = "El precio debe ser mayor a 0")
    private double price;
    @NotNull
    private Long brandId;
    @NotNull
    @Size(min = 1, max = 3, message = "Se debe tener al menos una categoria y máximo 3")
    private Set<Long> categoryIds;
}
