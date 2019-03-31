package software.jevera.domain.dto;

import org.mapstruct.Mapper;
import software.jevera.domain.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDto productDto);
}
