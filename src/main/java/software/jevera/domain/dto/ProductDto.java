package software.jevera.domain.dto;

import java.time.Instant;
import javax.validation.constraints.Max;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProductDto {
    @Length(max = 40, min = 5)
    private String name;
    @Length(max = 255)
    private String description;
    private Instant finishDate;
    private Integer startPrice;
}
