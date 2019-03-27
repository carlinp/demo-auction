package software.jevera.domain.dto;

import java.time.Instant;
import lombok.Data;
import software.jevera.domain.Product;

@Data
public class ProductDto {
    private String description;
    private Instant finishDate;
    private String name;
    private Integer startPrice;
}
