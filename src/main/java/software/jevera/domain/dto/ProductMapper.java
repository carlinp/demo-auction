package software.jevera.domain.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import software.jevera.domain.Product;

@Mapper(componentModel="spring")
public interface ProductMapper {

    Product toProduct(ProductDto dto);
}
