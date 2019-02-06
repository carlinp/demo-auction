package software.jevera.service.product;

import static software.jevera.service.product.ProductStateEnum.ARCHIVED;
import static software.jevera.service.product.ProductStateEnum.FINISHED;

import software.jevera.domain.Product;

public class Finished extends ProductState {
    @Override
    public void delete(Product product) {
        product.setStatus(ARCHIVED);
    }

    @Override
    public ProductStateEnum getStatusName() {
        return FINISHED;
    }
}
